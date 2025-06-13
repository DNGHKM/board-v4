package com.boardv4.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/*
    게시판 첨부 가능 파일 종류 관리
    게시판 별 허용 확장자 정보가 변경 되거나, 새로운 정책이 추가될 경우
    여기에 추가, 수정, 삭제 시 바로 반영 되도록 구현
 */
@AllArgsConstructor
@Getter
public enum FileType {
    ALL(List.of("jpg", "png", "gif", "zip")),
    IMAGE(List.of("jpg", "png", "gif")),
    NONE(List.of());

    private final List<String> allowedExtensions;
}