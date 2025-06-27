package com.boardv4.service;

import com.boardv4.domain.Category;
import com.boardv4.exception.category.CategoryNotFoundException;
import com.boardv4.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 카테고리 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategoriesByBoardId(Long boardId) {
        return categoryRepository.findByBoardId(boardId);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
