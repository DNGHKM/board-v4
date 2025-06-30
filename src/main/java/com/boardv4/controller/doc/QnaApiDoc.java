package com.boardv4.controller.doc;

import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.dto.post.PostViewCountResponse;
import com.boardv4.dto.qna.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "QnA API", description = "QnA 등록, 조회, 수정, 삭제 등의 기능을 제공합니다.")
public interface QnaApiDoc {

    @Operation(summary = "QnA 목록 조회", description = "검색 조건에 따라 QnA 게시글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QnA 목록 조회 성공")
    })
    ResponseEntity<ApiResponseDTO<QnaListResponse>> getQnaList(@ModelAttribute @Valid QnaSearchRequest request,
                                                               @Parameter(hidden = true) String username);

    @Operation(summary = "최신 QnA 목록 조회", description = "최신 QnA 게시글을 지정된 개수만큼 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "최신 QnA 목록 조회 성공"),
    })
    ResponseEntity<ApiResponseDTO<List<QnaSummaryResponse>>> getNewPostList(@RequestParam Integer limit);

    @Operation(summary = "QnA 상세 조회", description = "QnA 게시글의 상세 내용을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QnA 상세 조회 성공"),
    })
    ResponseEntity<ApiResponseDTO<QnaViewResponse>> getQnaView(@PathVariable Long qnaId,
                                                               @Parameter(hidden = true) String username);

    @Operation(summary = "QnA 작성", description = "로그인 사용자가 QnA를 등록합니다. 헤더에 accessToken을 포함해야 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "QnA 등록 성공"),
    })
    ResponseEntity<ApiResponseDTO<QnaWriteResponse>> writeQna(@ModelAttribute @Valid QnaWriteRequest writeDTO,
                                                              @Parameter(hidden = true) String username);

    @Operation(summary = "QnA 수정", description = "로그인 사용자가 답변이 달리지 않은 QnA를 수정합니다. 헤더에 accessToken을 포함해야 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QnA 수정 성공"),
    })
    ResponseEntity<ApiResponseDTO<QnaModifyResponse>> modifyQna(@PathVariable Long qnaId,
                                                                @ModelAttribute @Valid QnaModifyRequest modifyDTO,
                                                                @Parameter(hidden = true) String username);

    @Operation(summary = "QnA 삭제", description = "로그인 사용자가 답변이 달리지 않은 QnA를 삭제합니다. 헤더에 accessToken을 포함해야 합니다.")
    ResponseEntity<ApiResponseDTO<Void>> deleteQna(@PathVariable Long qnaId,
                                                   @Parameter(hidden = true) String username);

    @Operation(summary = "QnA 조회수 증가", description = "QnA 게시글의 조회수가 증가합니다.")
    ResponseEntity<ApiResponseDTO<PostViewCountResponse>> increaseViewCount(@PathVariable Long qnaId);
}
