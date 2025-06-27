package com.boardv4.mapper;

import com.boardv4.domain.Board;
import com.boardv4.dto.board.BoardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface BoardMapper {

    @Mappings({
            @Mapping(source = "id", target = "boardId"),
            @Mapping(source = "korName", target = "boardName"),
    })
    BoardResponse toDTO(Board board);
}
