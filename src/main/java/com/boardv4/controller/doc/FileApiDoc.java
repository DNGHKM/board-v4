package com.boardv4.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "파일 API", description = "게시글 첨부파일 다운로드 및 미리보기 기능을 제공합니다.")
public interface FileApiDoc {

    @Operation(
            summary = "파일 다운로드",
            description = "저장된 파일명을 기반으로 파일을 다운로드합니다. Content-Disposition 헤더로 첨부 파일로 처리됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 다운로드 성공"),
    })
    ResponseEntity<Resource> getPostView(
            @Parameter(description = "저장된 파일명(UUID.ext)", required = true, example = "d45a1a5e-84cd-4d0e-9b2e-fadf18428d55.pdf")
            @PathVariable String savedFilename
    );

    @Operation(
            summary = "파일 미리보기",
            description = "이미지 파일 등의 미리보기를 제공합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 미리보기 성공"),
    })
    ResponseEntity<Resource> preview(
            @Parameter(description = "저장된 파일명(UUID.ext)", required = true, example = "d45a1a5e-84cd-4d0e-9b2e-fadf18428d55.pdf")
            @PathVariable String savedFilename
    );
}
