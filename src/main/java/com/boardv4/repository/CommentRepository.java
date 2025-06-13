package com.boardv4.repository;

import com.boardv4.domain.Comment;
import com.boardv4.dto.comment.CommentResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentRepository {
    Optional<Comment> findById(Long commentId);

    void insert(Comment comment);

    void delete(Long commentId);

    List<CommentResponse> findResponseListByPostId(Long postId);
}
