package com.boardv4.controller;

import com.boardv4.annotation.LoginRequired;
import com.boardv4.annotation.LoginUser;
import com.boardv4.dto.post.*;
import com.boardv4.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

/*
    TODO 리스트에 보여줄 응답 구현해야 함(페이징)
        응답은 여러 화면에서 공통으로 사용하기 위해(표시할 항목이 일반 게시판과 갤러리가 다름)
        썸네일사진 파일명(갤러리), 첨부파일 갯수(갤러리), 댓글 갯수(자유게시판) 모두 포함해야 함
 */


    //    @GetMapping
//    public ResponseEntity<ApiResponse<ListPostResponse>> getPostList(@ModelAttribute @Valid SearchPostRequest listRequestDTO) {
//        ListPostResponse dto = postService.getPostList(listRequestDTO);
//        return ResponseEntity.ok(ApiResponse.success("게시글을 목록을 조회하였습니다.", dto));
//    }
//
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostViewResponse>> getPostView(@PathVariable Long postId) {
        PostViewResponse dto = postService.getPostViewById(postId);
        return ResponseEntity.ok(ApiResponse.success("게시글을 조회하였습니다.", dto));
    }

    /*
        로그인 검증과 username 파라미터 바인딩
        [최초 구현 순서]
            1. @LoginRequired AOP
                - 로그인 여부 확인 + request에 username 추가
            2. @LoginUser ArgumentResolver
                - username 파라미터 바인딩
        --
            @LoginRequired가 달린 메서드에 AOP 설정하여 로그인 여부 확인, request에 username 등록함 ->
            각 컨트롤러에서 반복적으로 (String) request.getAttribute("username") 으로 회원 정보를 꺼내기 보다는,
            ArgumentResolver가 request 내부의 "username" 꺼내서 파라미터 바인딩 해주길 기대함
            그러나 ArgumentResolver -> @LoginRequired AOP 순으로 실행 되어
            @LoginUser String username 으로 값을 꺼내올 수 없었음
        --
        [수정]
            1. JwtAuthFilter(추가)
                - 헤더에서 토큰 확인 후 request에 username을 추가함
            2. @LoginUser ArgumentResolver
                - username 파라미터 바인딩
            3. @LoginRequired AOP
                - 로그인 여부 확인
     */
    @LoginRequired
    @PostMapping
    public ResponseEntity<ApiResponse<PostWriteResponse>> writePost(@ModelAttribute @Valid PostWriteRequest writeDTO,
                                                                    @LoginUser String username) {
        PostWriteResponse dto = postService.write(username, writeDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("게시글을 등록하였습니다.", dto));
    }

    @LoginRequired
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> modifyPost(@PathVariable Long postId,
                                                        @ModelAttribute @Valid PostModifyRequest modifyDTO,
                                                        @LoginUser String username) {
        postService.modify(postId, modifyDTO, username);
        return ResponseEntity.ok(ApiResponse.success("게시글을 수정하였습니다."));
    }

    @LoginRequired
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId,
                                                        @LoginUser String username) {
        postService.delete(postId, username);
        return ResponseEntity.ok(ApiResponse.success("게시글을 삭제하였습니다."));
    }

    @PostMapping("/{postId}/viewCount")
    public ResponseEntity<ApiResponse<PostViewCountResponse>> increaseViewCount(@PathVariable Long postId) {
        Integer viewCount = postService.increaseViewCount(postId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("조회수가 증가되었습니다.",
                        PostViewCountResponse.from(viewCount)));
    }
}
