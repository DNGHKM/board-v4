package com.boardv4.domain;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private Long boardId;
    private String name;
}
