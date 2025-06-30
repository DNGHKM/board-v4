package com.boardv4.controller;

import com.boardv4.controller.doc.AuthApiDoc;
import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.dto.auth.*;
import com.boardv4.service.AuthService;
import com.boardv4.service.MemberService;
import com.boardv4.validator.SignUpRequestValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDoc {
    private final AuthService authService;
    private final MemberService memberService;
    private final SignUpRequestValidator signUpRequestValidator;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok((new ApiResponseDTO<>(true, "로그인 성공", response)));
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponseDTO<Boolean>> login(@RequestParam String username) {
        String message = "사용 가능한 아이디 입니다.";
        boolean result = memberService.isUsernameAvailable(username);
        if (!result) {
            message = "사용 불가능한 아이디 입니다.";
        }
        return ResponseEntity.ok(ApiResponseDTO.success(message, result));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<SignUpResponse>> signUp(@RequestBody @Valid SignUpRequest request) {
        signUpRequestValidator.validate(request);
        SignUpResponse response = authService.signUp(request);
        return ResponseEntity.ok(ApiResponseDTO.success("회원가입 성공", response));
    }

    @PostMapping("/me")
    public ResponseEntity<ApiResponseDTO<ValidTokenResponse>> validToken(@RequestBody ValidTokenRequest request) {
        ValidTokenResponse response = authService.validAndGetUsername(request);
        return ResponseEntity.ok(ApiResponseDTO.success("토큰 검증 성공", response));
    }
}
