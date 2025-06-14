package com.boardv4.service;

import com.boardv4.domain.Member;
import com.boardv4.dto.auth.SignUpRequest;
import com.boardv4.enums.Role;
import com.boardv4.security.JwtUtil;
import com.boardv4.util.PasswordUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public String login(String username, String inputPassword) {
        Member member = memberService.getMemberByUsername(username);

        PasswordUtil.validatePassword(inputPassword, member.getPassword());

        return jwtUtil.generateToken(member.getUsername());
    }

    /*
        비밀번호 암호화 위치에 대하여
        1. 서비스에서 암호화 후 객체 생성
            - 암호화 책임이 도메인 내부보다는, 외부 서비스에 있다고 판단
            - 불변 객체를 유지할 수 있음
        2. Mapper(MapStruct)에서 암호화 처리
            - Mapper는 단순한 DTO ↔ Entity 매핑 역할(비즈니스 로직 침투 부적절)
            - Sha256Util 등 외부 의존이 Mapper에 들어가는 것은 SRP 위배(?)
        3. Mapper로 Member 생성 후, setter로 암호화된 비밀번호 주입
            - 불변 객체 설계를 지향하고 있어 setter가 없음 → 불가능
        4. Member 도메인 내부에서 비밀번호를 암호화하는 팩토리 메서드 제공 (ex. Member.create(...))
            - 비밀번호 암호화 정책이 도메인 내부로 침투
        결론: 1번(Service에서 암호화 후 객체 생성)을 선택
            → 불변 객체 설계 유지 + 암호화 책임을 명확히 분리
            → Mapper와 도메인 모두 순수하게 유지할 수 있는 구조
    */
    public String signUp(SignUpRequest request) {
        memberService.validateUsernameAvailable(request.getUsername());

        String encryptedPassword = PasswordUtil.encrypt(request.getPassword());

        Member member = Member.builder()
                .username(request.getUsername())
                .name(request.getName())
                .password(encryptedPassword)
                .role(Role.USER)
                .build();

        memberService.save(member);
        return jwtUtil.generateToken(member.getUsername());
    }
}
