package com.kelton.clonnit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {

    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;

}
