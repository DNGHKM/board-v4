package com.boardv4.repository;

import com.boardv4.domain.Qna;
import com.boardv4.dto.qna.QnaSearchRequest;
import com.boardv4.dto.qna.QnaSummaryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface QnaRepository {
    Optional<Qna> findById(Long qnaId);

    void insert(Qna qna);

    void update(Qna qna);

    void delete(Long qnaId);

    int countBySearch(QnaSearchRequest dto, String username);

    List<QnaSummaryResponse> findBySearch(QnaSearchRequest dto, int offset, String username);

    void increaseViewCount(Long qnaId);

    Integer findViewCountById(Long qnaId);

    List<QnaSummaryResponse> findLatestQnaList(Integer limit);
}
