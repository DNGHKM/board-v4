package com.boardv4.repository;

import com.boardv4.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findByUsername(String username);

    Optional<Member> findById(Long id);

    void insert(Member member);
}
