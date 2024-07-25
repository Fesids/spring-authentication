package com.application.app.models.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors
@AllArgsConstructor
@Setter
@Getter
public class AuthTokenResponse {

    private String accessToken;

    private String refreshToken;

    private long expiresIn;

}
