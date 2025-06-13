package com.boardv4.mapper;

import com.boardv4.domain.Post;
import com.boardv4.dto.post.PostWriteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

// 다른 클래스들은 단순히 DTO -> entity 변환이 아닌, 추가적인 값들을 더 필요로 함.
// 완전한 자동이 아닌 이상에야, mapper를 따로 만드는 것에 대한 이점?
@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface PostMapper {

    @Mappings({
            @Mapping(target = "viewCount", constant = "0"),
            @Mapping(target = "createAt", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "deleted", constant = "false"),
    })
    Post toEntity(Long memberId, PostWriteRequest dto);
}
