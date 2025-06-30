package com.boardv4.controller.doc;

import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.dto.auth.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "인증 API", description = "로그인, 회원가입, 토큰 검증 관련 API")
public interface AuthApiDoc {

    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인합니다.")
    ResponseEntity<ApiResponseDTO<LoginResponse>> login(@RequestBody LoginRequest request);

    @Operation(summary = "아이디 중복확인", description = "회원가입 시 아이디 중복 여부를 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "중복 확인 결과 반환")
    })
    ResponseEntity<ApiResponseDTO<Boolean>> login(@RequestParam String username);

    @Operation(summary = "회원가입", description = "아이디, 비밀번호, 이름을 받아 회원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
    })
    ResponseEntity<ApiResponseDTO<SignUpResponse>> signUp(@RequestBody SignUpRequest request);

    @Operation(summary = "토큰 검증", description = "전달받은 액세스 토큰의 유효성을 검증하고 사용자 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 유효"),
    })
    ResponseEntity<ApiResponseDTO<ValidTokenResponse>> validToken(@RequestBody ValidTokenRequest request);
}
