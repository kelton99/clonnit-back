package com.kelton.clonnit.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class JwtProvider {

	private final JwtEncoder jwtEncoder;
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;

	public JwtProvider(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	public String generateToken(Authentication authentication) {
		final User principal = (User) authentication.getPrincipal();
		return generateTokenWithUserName(principal.getUsername());
	}

	private String generateTokenWithUserName(String username) {
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(Instant.now())
				.expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
				.subject(username)
				.claim("scope", "ROLE_USER")
				.build();

		return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}
}