package com.boardv4.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidTokenRequest {
    String token;
}
