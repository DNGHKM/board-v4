package com.boardv4.repository;

import com.boardv4.domain.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface PostRepository {
    Optional<Post> findById(Long postId);

    void insert(Post post);

    void update(Post post);

    Integer findViewCountById(Long postId);

    void softDelete(Long id, String softDeletedSubject, String softDeletedContent);

    void hardDelete(Long postId);

    void increaseViewCount(Long postId);
}
