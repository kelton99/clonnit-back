package com.kelton.clonnit.controller;

import com.kelton.clonnit.dto.AuthenticationResponse;
import com.kelton.clonnit.dto.LoginRequest;
import com.kelton.clonnit.dto.RefreshTokenRequest;
import com.kelton.clonnit.dto.RegisterRequest;
import com.kelton.clonnit.service.AuthService;
import com.kelton.clonnit.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;

	public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
		this.authService = authService;
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
	}

	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

	@PostMapping("/refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshToken(refreshTokenRequest);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully");
	}
}