package com.boardv4.mapper;

import com.boardv4.domain.Comment;
import com.boardv4.dto.comment.CommentWriteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "createAt", expression = "java(LocalDateTime.now())"),
    })
    Comment toEntity(Long postId, Long memberId, CommentWriteRequest commentWriteRequest);
}
