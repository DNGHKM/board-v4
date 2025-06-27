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

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 작성, 조회, 수정, 삭제, 파일 처리 및 권한 검증 등의 기능을 제공합니다.
 */
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

    /**
     * 게시글 조회 화면을 위한 상세 조회를 수행합니다.
     *
     * @param postId 게시글 ID
     * @return 게시글 상세 정보
     */
    public PostViewResponse getPostViewById(Long postId) {
        // 1. 게시글 찾기
        Post post = getPostById(postId);

        // 2. 작성자 정보 찾기
        Member member = memberService.getMemberById(post.getMemberId());

        // 3. 카테고리 찾기(화면에 표시할 카테고리 이름을 넣어주기 위함)
        Category category = categoryService.getCategoryById(post.getCategoryId());

        // 4. 첨부파일 목록 찾기
        List<PostFile> files = postFileService.getFilesByPostId(postId);

        // 5. 응답 구성 및 반환
        return PostViewResponse.from(post, member.getUsername(), member.getName(), category, files);
    }

    /**
     * 상단 고정된 게시글을 조회합니다.(5개)
     *
     * @param boardId 게시판 ID
     * @return 고정된 게시글 목록
     */
    public List<PostSummaryResponse> getPinnedPostList(Long boardId) {
        return postRepository.findPinnedTop5(boardId);
    }

    /**
     * 최신 게시글 목록을 조회합니다.
     *
     * @param boardId 게시판 ID
     * @param limit   조회 제한 수
     * @return 최신 게시글 목록
     */
    public List<PostSummaryResponse> getLatestPostList(Long boardId, Integer limit) {
        return postRepository.findLatestPostList(boardId, limit);
    }

    /**
     * 게시글 목록(페이징/검색 조건 포함)을 조회합니다.
     *
     * @param request 검색 및 페이징 조건
     * @return 게시글 목록 응답
     */
    public PostListResponse getPostList(PostSearchRequest request) {
        // 검색 결과 개수 조회
        int totalCount = postRepository.countBySearch(request);

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
        return PostListResponse.from(request, totalPages, totalCount, posts);
    }

    public PostWriteResponse write(String username, PostWriteRequest writeDTO) {
        // 1. 게시판 정보 조회 및 게시판 정책 준수 여부 확인
        Board board = boardService.getBoardById(writeDTO.getBoardId());
        boardPolicyValidator.validate(writeDTO.getFiles(), board);

        // 2. 해당 카테고리가 Board에 속해있는지 확인
        Set<Long> categoryIds = categoryService.getAllCategoriesByBoardId(writeDTO.getBoardId())
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
        return PostWriteResponse.from(board.getId(), post.getId());
    }

    public Integer increaseViewCount(Long id) {
        postRepository.increaseViewCount(id);
        return postRepository.findViewCountById(id);
    }

    /**
     * 게시글을 수정합니다.
     *
     * @param postId    게시글 ID
     * @param modifyDTO 수정 정보
     * @param username  수정 요청자
     * @return 수정 결과
     */
    public PostModifyResponse modify(Long postId, PostModifyRequest modifyDTO, String username) {
        // 1. 게시글 조회
        Post post = getPostById(postId);

        // 2. 이미 소프트 딜리트 된 게시글의 경우 수정 불가
        if (post.isDeleted()) {
            throw new DeletedPostException(postId);
        }

        // 3. 해당 요청을 게시판이 호환가능한지 판단 (파일첨부)
        Board board = boardService.getBoardById(post.getBoardId());
        boardPolicyValidator.validate(modifyDTO.getFiles(), board);

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

        return PostModifyResponse.from(board.getId(), post.getId());
    }

    /**
     * 게시글을 삭제합니다.
     * 댓글이 있는 경우 소프트 딜리트 처리, 없으면 완전 삭제합니다.
     *
     * @param postId   게시글 ID
     * @param username 삭제 요청자
     */
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

    /**
     * 게시글 소유자인지 검증합니다.
     *
     * @param username 사용자명
     * @param post     게시글 엔티티
     */
    private void verifyOwner(String username, Post post) {
        Member member = memberService.getMemberByUsername(username);
        if (!Objects.equals(post.getMemberId(), member.getId())) {
            throw new ForbiddenException();
        }
    }
}
