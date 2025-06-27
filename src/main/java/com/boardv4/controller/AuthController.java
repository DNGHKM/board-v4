package com.boardv4.controller;

import com.boardv4.dto.auth.*;
import com.boardv4.service.AuthService;
import com.boardv4.service.MemberService;
import com.boardv4.validator.SignUpRequestValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "인증 API", description = "로그인, 회원가입, 토큰 검증 관련 API")
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final SignUpRequestValidator signUpRequestValidator;

    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request.getUsername(), request.getPassword());

        return ResponseEntity.ok().body(
                new ApiResponseDTO<>(true, "로그인 성공", response)
        );
    }

    @Operation(summary = "아이디 중복확인", description = "회원가입 시 아이디 중복 여부를 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "중복 확인 결과 반환")
    })
    @GetMapping("/check")
    public ResponseEntity<ApiResponseDTO<Boolean>> login(@RequestParam String username) {
        String message = "사용 가능한 아이디 입니다.";

        boolean result = memberService.isUsernameAvailable(username);
        if (!result) {
            message = "사용 불가능한 아이디 입니다.";
        }
        return ResponseEntity.ok().body(new ApiResponseDTO<>(true, message, result));
    }

    @Operation(summary = "회원가입", description = "아이디, 비밀번호, 이름을 받아 회원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패 또는 중복 아이디")
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<SignUpResponse>> signUp(@RequestBody @Valid SignUpRequest request) {
        signUpRequestValidator.validate(request);

        SignUpResponse response = authService.signUp(request);

        return ResponseEntity.ok().body(
                new ApiResponseDTO<>(true, "회원가입 성공", response)
        );
    }

    @Operation(summary = "토큰 검증", description = "전달받은 액세스 토큰의 유효성을 검증하고 사용자 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 유효"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰 또는 만료됨")
    })
    @PostMapping("/me")
    public ResponseEntity<ApiResponseDTO<ValidTokenResponse>> validToken(@RequestBody ValidTokenRequest request) {
        ValidTokenResponse response = authService.validAndGetUsername(request);

        return ResponseEntity.ok().body(
                new ApiResponseDTO<>(true, "토큰 확인 성공", response)
        );
    }
}
