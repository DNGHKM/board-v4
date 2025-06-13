package com.boardv4.repository;

import com.boardv4.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface BoardRepository {
    Optional<Board> findById(Long boardId);

    Board findByEngName(String engName);
}
