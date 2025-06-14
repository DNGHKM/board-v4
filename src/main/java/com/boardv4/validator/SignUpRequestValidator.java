package com.boardv4.validator;

import com.boardv4.dto.auth.SignUpRequest;
import com.boardv4.exception.base.FieldValidationException;
import org.springframework.stereotype.Component;

@Component
public class SignUpRequestValidator {

    /*
      TODO 회원가입 요청 비밀번호 특수룰 검증
        (특수룰 - 아이디와 동일한 문자 불가능, 동일 문자 연속 3번 이상 불가)
     */
    public void validate(SignUpRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String passwordCheck = request.getPasswordCheck();

        if (!password.equals(passwordCheck)) {
            throw new FieldValidationException("password", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        if (username.equals(password)) {
            throw new FieldValidationException("password", "아이디와 동일한 비밀번호는 사용할 수 없습니다.");
        }

        if (hasCharTripleRepeat(password)) {
            throw new FieldValidationException("password", "동일 문자를 연속 3번 이상 사용할 수 없습니다.");
        }
    }

    private boolean hasCharTripleRepeat(String password) {
        int count = 1;
        for (int i = 1; i < password.length(); i++) {
            if (password.charAt(i) != password.charAt(i - 1)) {
                count = 1;
                continue;
            }
            count++;
            if (count == 3) {
                return true;
            }
        }
        return false;
    }
}
