package com.boardv4.controller;

import com.boardv4.controller.doc.CategoryApiDoc;
import com.boardv4.domain.Category;
import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController implements CategoryApiDoc {

    private final CategoryService categoryService;

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponseDTO<List<Category>>> getAllCategories(@PathVariable Long boardId) {
        List<Category> categories = categoryService.getAllCategoriesByBoardId(boardId);
        return ResponseEntity.ok(ApiResponseDTO.success("카테고리를 조회하였습니다.", categories));
    }
}
