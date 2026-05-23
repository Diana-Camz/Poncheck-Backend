package com.poncheck.dto.response.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseDTO(
        @JsonProperty("access_token")
        String token,
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
