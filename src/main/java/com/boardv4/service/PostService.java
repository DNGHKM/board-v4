package com.boardv4.service;

import com.boardv4.domain.*;
import com.boardv4.dto.post.*;
import com.boardv4.exception.base.FieldValidationException;
import com.boardv4.exception.base.ForbiddenException;
import com.boardv4.exception.post.DeletedPostException;
import com.boardv4.exception.post.PostNotFoundException;
import com.boardv4.mapper.PostMapper;
import com.boardv4.repository.PostRepository;
import com.boardv4.validator.BoardPolicyValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final MemberService memberService;
    private final BoardService boardService;
    private final PostFileService postFileService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final BoardPolicyValidator boardPolicyValidator;

    private final String SOFT_DELETED_SUBJECT = "[삭제된 게시글입니다.]";
    private final String SOFT_DELETED_CONTENT = "삭제된 게시글입니다.";

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    /*
        게시판 정책 준수 여부 확인을 컨트롤러에? 서비스에?
        컨트롤러에서 사전에 게시판 정책을 준수하는지 Validation을 수행하는걸 고민해봤으나
        게시판 정책을 준수하는지 여부는 서비스 로직에 가깝고
        컨트롤러의 역할은 어디까지나 요청을 받고 필요한 서비스에 위임하는 것이므로
        서비스에서 수행하기로 함
     */
    public PostWriteResponse write(String username, PostWriteRequest writeDTO) {
        // 1. 게시판 정보 조회 및 게시판 정책 준수 여부 확인
        Board board = boardService.getBoardById(writeDTO.getBoardId());
        boardPolicyValidator.validate(writeDTO.isPinned(), writeDTO.getFiles(), board);

        // 2. 해당 카테고리가 Board에 속해있는지 확인
        Set<Long> categoryIds = categoryService.getAllCategoriesByBoard(writeDTO.getBoardId())
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        if (!categoryIds.contains(writeDTO.getCategoryId())) {
            throw new FieldValidationException("categoryId", "해당 게시판에 속하지 않은 카테고리입니다.");
        }


        // 3. 작성자 정보 확인 및 게시글 저장
        Long memberId = memberService.getMemberByUsername(username).getId();
        Post post = postMapper.toEntity(memberId, writeDTO);
        postRepository.insert(post);

        // 4. 파일 저장
        if (writeDTO.getFiles() != null) {
            postFileService.uploadMultipleFile(board.getEngName(), post.getId(), writeDTO.getFiles());
        }

        // 5. 응답 객체 구성 및 반환
        return PostWriteResponse.from(post.getId(), board.getEngName());
    }

    public PostViewResponse getPostViewById(Long postId) {
        // 1. 게시글 찾기
        Post post = getPostById(postId);

        // 2. 카테고리 찾기(화면에 표시할 카테고리 이름을 넣어주기 위함)
        Category category = categoryService.getCategoryById(post.getCategoryId());

        // 3. 첨부파일 목록 찾기
        List<PostFile> files = postFileService.getFilesByPostId(postId);

        // 4. 응답 구성 및 반환
        return PostViewResponse.from(post, category.getName(), files);
    }

    public PostListResponse getPostList(PostSearchRequest request) {
        //게시판 가져오기
        Board board = boardService.getBoardById(request.getBoardId());

        // 검색 결과 개수 조회
        int totalCount = postRepository.countBySearch(request);
        log.info("전체 게시글 갯수 = {}", totalCount);

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / request.getSize());

        //페이지 수 보정(1 미만 혹은 너무 큰 페이지 넘버가 들어오는 경우)
        request.adjustPage(totalPages);

        // 페이징 계산
        int offset = Math.min(
                (request.getPage() - 1) * request.getSize(),
                (totalCount / request.getSize()) * request.getSize()
        );

        // 게시글 목록 조회
        List<PostSummaryResponse> posts = postRepository.findBySearch(request, offset);

        // 응답 DTO 구성
        return PostListResponse.from(request, board.getEngName(), totalPages, totalCount, posts);
    }

    public Integer increaseViewCount(Long id) {
        postRepository.increaseViewCount(id);
        return postRepository.findViewCountById(id);
    }

    public void modify(Long postId, PostModifyRequest modifyDTO, String username) {
        // 1. 게시글 조회
        Post post = getPostById(postId);

        // 2. 이미 소프트 딜리트 된 게시글의 경우 수정 불가
        if (post.isDeleted()) {
            throw new DeletedPostException(postId);
        }

        // 3. 해당 요청을 게시판이 호환가능한지 판단 (요청상단고정, 파일첨부)
        Board board = boardService.getBoardById(post.getBoardId());
        boardPolicyValidator.validate(modifyDTO.isPinned(), modifyDTO.getFiles(), board);

        // 4. 작성자인지 확인(수정 권한 검증)
        verifyOwner(username, post);

        // 5. 게시글 정보 수정
        post.update(modifyDTO);
        postRepository.update(post);

        // 6. 삭제된 첨부파일 정리
        postFileService.removeDeletedFiles(post.getId(), modifyDTO);

        // 7. 추가된 첨부파일 등록
        if (!CollectionUtils.isEmpty(modifyDTO.getFiles())) {
            postFileService.uploadMultipleFile(board.getEngName(), post.getId(), modifyDTO.getFiles());
        }
    }

    public void delete(Long postId, String username) {
        Post post = getPostById(postId);

        // 1. 소프트 딜리트 된 게시글의 경우 삭제 불가
        if (post.isDeleted()) {
            throw new DeletedPostException(postId);
        }

        // 2. 삭제 권한 검증(사용자 확인)
        verifyOwner(username, post);

        // 3. 파일 삭제
        postFileService.deleteByPostId(post.getId());

        // 4-1. 댓글 있는 경우 제목, 내용만 삭제(소프트 딜리트)
        if (!commentService.getCommentsByPostId(postId).isEmpty()) {
            postRepository.softDelete(post.getId(), SOFT_DELETED_SUBJECT, SOFT_DELETED_CONTENT);
            return;
        }

        // 4-2. 댓글 없는 경우 완전 삭제
        postRepository.hardDelete(post.getId());
    }


    /*
        해당 게시판의 정책을 준수하는 요청이 들어왔는지 확인 함
        프론트에서 검증을 뚫고 들어온 요청에 대해선 존중 할 필요 없으니 바로 게시글 작성/수정 실패 처리
     */
    private void verifyOwner(String username, Post post) {
        Member member = memberService.getMemberByUsername(username);
        if (!Objects.equals(post.getMemberId(), member.getId())) {
            throw new ForbiddenException();
        }
    }
}
