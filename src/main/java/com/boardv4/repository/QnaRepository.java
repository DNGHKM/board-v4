package com.boardv4.repository;

import com.boardv4.domain.Qna;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface QnaRepository {
    Optional<Qna> findById(Long qnaId);

    void insert(Qna qna);

    void update(Qna qna);

    void delete(Long qnaId);
}
