package com.boardv4.controller;

import com.boardv4.annotation.LoginRequired;
import com.boardv4.annotation.LoginUser;
import com.boardv4.dto.post.*;
import com.boardv4.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO 이렇게 상세하게 설명을 다 쓰는게 맞나...
@Tag(name = "게시글 API", description = "게시글 등록, 조회, 수정, 삭제 등의 기능을 제공합니다.")
@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 목록 조회", description = "검색 조건에 따라 게시글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO<PostListResponse>> getPostList(@ModelAttribute @Valid PostSearchRequest request) {
        PostListResponse response = postService.getPostList(request);
        return ResponseEntity.ok(ApiResponseDTO.success("게시글을 목록을 조회하였습니다.", response));
    }

    @Operation(summary = "최신 게시글 조회", description = "특정 게시판에서 최신 게시글을 지정한 개수만큼 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "최신 게시글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "limit 파라미터 오류")
    })
    @GetMapping("/{boardId}/new")
    public ResponseEntity<ApiResponseDTO<List<PostSummaryResponse>>> getNewPostList(@PathVariable Long boardId,
                                                                                    @RequestParam Integer limit) {
        List<PostSummaryResponse> response = postService.getLatestPostList(boardId, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("최근 게시글을 목록을 조회하였습니다.", response));
    }

    @Operation(summary = "고정 게시글 조회", description = "특정 게시판의 상단 고정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고정 게시글 조회 성공")
    })
    @GetMapping("/{boardId}/pinned")
    public ResponseEntity<ApiResponseDTO<List<PostSummaryResponse>>> getPinnedPostList(@PathVariable Long boardId) {
        List<PostSummaryResponse> response = postService.getPinnedPostList(boardId);
        return ResponseEntity.ok(ApiResponseDTO.success("고정 게시글을 목록을 조회하였습니다.", response));
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 게시글 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<PostViewResponse>> getPostView(@PathVariable Long postId) {
        PostViewResponse dto = postService.getPostViewById(postId);
        return ResponseEntity.ok(ApiResponseDTO.success("게시글을 조회하였습니다.", dto));
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
    @Operation(summary = "게시글 등록", description = "로그인한 사용자가 게시글을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @LoginRequired
    @PostMapping
    public ResponseEntity<ApiResponseDTO<PostWriteResponse>> writePost(@ModelAttribute @Valid PostWriteRequest writeDTO,
                                                                       @Parameter(hidden = true) @LoginUser String username) {
        PostWriteResponse dto = postService.write(username, writeDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("게시글을 등록하였습니다.", dto));
    }

    @Operation(summary = "게시글 수정", description = "로그인한 사용자가 게시글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "게시글 수정 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음")
    })
    @LoginRequired
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<PostModifyResponse>> modifyPost(@PathVariable Long postId,
                                                                         @ModelAttribute @Valid PostModifyRequest modifyDTO,
                                                                         @Parameter(hidden = true) @LoginUser String username) {
        PostModifyResponse response = postService.modify(postId, modifyDTO, username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success("게시글을 수정하였습니다.", response));
    }

    @Operation(summary = "게시글 삭제", description = "로그인한 사용자가 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "게시글 삭제 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음")
    })
    @LoginRequired
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePost(@PathVariable Long postId,
                                                           @Parameter(hidden = true) @LoginUser String username) {
        postService.delete(postId, username);
        return ResponseEntity.ok(ApiResponseDTO.success("게시글을 삭제하였습니다."));
    }

    @Operation(summary = "게시글 조회수 증가", description = "해당 게시글의 조회수를 1 증가시킵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "조회수 증가 성공"),
            @ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음")
    })
    @PostMapping("/{postId}/viewCount")
    public ResponseEntity<ApiResponseDTO<PostViewCountResponse>> increaseViewCount(@PathVariable Long postId) {
        Integer viewCount = postService.increaseViewCount(postId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("조회수가 증가되었습니다.",
                        PostViewCountResponse.from(viewCount)));
    }
}
