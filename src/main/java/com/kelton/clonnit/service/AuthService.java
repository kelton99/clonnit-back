package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.AuthenticationResponse;
import com.kelton.clonnit.dto.LoginRequest;
import com.kelton.clonnit.dto.RefreshTokenRequest;
import com.kelton.clonnit.dto.RegisterRequest;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Clonnitor;
import com.kelton.clonnit.repository.ClonnitorRepository;
import com.kelton.clonnit.security.JwtProvider;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final ClonnitorRepository clonnitorRepository;
    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthService(PasswordEncoder passwordEncoder, ClonnitorRepository clonnitorRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.clonnitorRepository = clonnitorRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        final Clonnitor clonnitor = new Clonnitor();
        clonnitor.setUsername(registerRequest.getUsername());
        clonnitor.setEmail(registerRequest.getEmail());
        clonnitor.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        clonnitor.setCreated(LocalDateTime.now());
        clonnitor.setEnabled(true);

        clonnitorRepository.save(clonnitor);
    }

    @Transactional
    public Clonnitor getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return clonnitorRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new ClonnitException("User name not found - " + principal.getSubject()));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return new AuthenticationResponse(token,
                refreshTokenService.generateRefreshToken().getToken(),
                Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
                loginRequest.getUsername()
        );
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        final String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticationToken(token);
        authenticationResponse.setRefreshToken(refreshTokenRequest.getRefreshToken());
        authenticationResponse.setExpiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()));
        authenticationResponse.setUsername(refreshTokenRequest.getUsername());
        return authenticationResponse;
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


}