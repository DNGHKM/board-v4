package com.boardv4.mapper;

import com.boardv4.domain.Qna;
import com.boardv4.dto.qna.QnaViewResponse;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface QnaMapper {
    QnaViewResponse toViewDTO(Qna qna, String writerName, String answererName);
}
