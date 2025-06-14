package com.boardv4.controller;

import com.boardv4.dto.auth.LoginRequest;
import com.boardv4.dto.auth.LoginResponse;
import com.boardv4.dto.auth.SignUpRequest;
import com.boardv4.dto.auth.SignUpResponse;
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
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final SignUpRequestValidator signUpRequestValidator;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        String accessToken = authService.login(request.getUsername(), request.getPassword());

        return ResponseEntity.ok().body(
                new ApiResponse<>(true, "로그인 성공", new LoginResponse(accessToken))
        );
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Boolean>> login(@RequestParam String username) {
        String message = "사용 가능한 아이디 입니다.";

        boolean result = memberService.isUsernameAvailable(username);
        if (!result) {
            message = "사용 불가능한 아이디 입니다.";
        }
        return ResponseEntity.ok().body(new ApiResponse<>(true, message, result));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@RequestBody @Valid SignUpRequest request) {
        signUpRequestValidator.validate(request);

        String accessToken = authService.signUp(request);

        return ResponseEntity.ok().body(
                new ApiResponse<>(true, "회원가입 성공", new SignUpResponse(accessToken))
        );
    }
}
