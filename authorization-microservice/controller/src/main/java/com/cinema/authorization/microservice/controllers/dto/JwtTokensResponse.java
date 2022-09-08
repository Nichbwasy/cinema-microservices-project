package com.cinema.authorization.microservice.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokensResponse {
    private String accessToken;
    private String refreshToken;

}
