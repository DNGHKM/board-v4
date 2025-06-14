package com.boardv4.service;

import com.boardv4.domain.Member;
import com.boardv4.exception.base.FieldValidationException;
import com.boardv4.exception.member.MemberNotFoundException;
import com.boardv4.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public boolean isUsernameAvailable(String username) {
        return memberRepository.findByUsername(username).isEmpty()
                && !isForbiddenUsername(username);
    }

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

    private boolean isForbiddenUsername(String username) {
        String[] forbidden = {"admin", "root", "system", "null"};
        for (String reserved : forbidden) {
            if (reserved.contains(username)) {
                return true;
            }
        }
        return false;
    }

    public void save(Member member) {
        memberRepository.insert(member);
    }
}
