package com.boardv4.repository;

import com.boardv4.domain.PostFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostFileRepository {
    void insertFiles(List<PostFile> postFiles);

    List<PostFile> findByPostId(Long postId);

    Optional<PostFile> findBySavedFilename(String savedFilename);

    void deleteById(Long id);
}
