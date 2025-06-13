package com.boardv4.repository;

import com.boardv4.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CategoryRepository {
    List<Category> findByBoardId(Long boardId);

    Optional<Category> findById(Long categoryId);
}
