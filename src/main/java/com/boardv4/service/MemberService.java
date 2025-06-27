package com.boardv4.service;

import com.boardv4.domain.Member;
import com.boardv4.exception.base.FieldValidationException;
import com.boardv4.exception.member.MemberNotFoundException;
import com.boardv4.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원(Member) 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
    }

    public void save(Member member) {
        memberRepository.insert(member);
    }


    /**
     * 아이디 중복 여부 및 금지 아이디 여부를 확인합니다.
     *
     * @param username 중복 또는 금지된 아이디인지 확인할 사용자명
     * @return 사용 가능한 경우 true, 그렇지 않으면 false
     */
    public boolean isUsernameAvailable(String username) {
        return memberRepository.findByUsername(username).isEmpty()
                && !isForbiddenUsername(username);
    }

    /**
     * 아이디 사용 가능 여부를 검증하고, 불가능한 경우 FieldValidationException 예외를 발생시킵니다.
     *
     * @param username 검증할 사용자명
     * @throws FieldValidationException 아이디가 중복되었거나 금지된 경우
     */
    public void validateUsernameAvailable(String username) {
        // 중복 검사
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new FieldValidationException("username", "이미 사용 중인 아이디입니다.");
        }

        // 사용 불가 목록 검사 -> DB에 별도로 관리 또는 프로퍼티 파일
        if (isForbiddenUsername(username)) {
            throw new FieldValidationException("username", "사용할 수 없는 아이디입니다.");
        }
    }


    /**
     * 금지된 아이디 목록에 포함되는지 확인합니다.
     *
     * @param username 확인할 사용자명
     * @return 금지된 경우 true, 그렇지 않으면 false
     */
    private boolean isForbiddenUsername(String username) {
        String[] forbidden = {"admin", "root", "system", "null"};
        for (String reserved : forbidden) {
            if (reserved.contains(username)) {
                return true;
            }
        }
        return false;
    }
}
