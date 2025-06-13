package com.boardv4.domain;

import com.boardv4.enums.Role;
import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Long id;
    private String username;
    private String name;
    private String password;
    private Role role;
}
