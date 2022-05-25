package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.AuthenticationResponse;
import com.kelton.clonnit.dto.LoginRequest;
import com.kelton.clonnit.dto.RegisterRequest;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Clonnitor;
import com.kelton.clonnit.model.NotificationEmail;
import com.kelton.clonnit.model.VerificationToken;
import com.kelton.clonnit.repository.ClonnitorRepository;
import com.kelton.clonnit.repository.VerificationTokenRepository;
import com.kelton.clonnit.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final ClonnitorRepository clonnitorRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final AuthenticationManager authenticationManager;

	private final JwtProvider jwtProvider;
	private final MailService mailService;

	public AuthService(PasswordEncoder passwordEncoder, ClonnitorRepository clonnitorRepository, VerificationTokenRepository verificationTokenRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, MailService mailService) {
		this.passwordEncoder = passwordEncoder;
		this.clonnitorRepository = clonnitorRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.mailService = mailService;
	}

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		final Clonnitor clonnitor = new Clonnitor();
		clonnitor.setUsername(registerRequest.getUsername());
		clonnitor.setEmail(registerRequest.getEmail());
		clonnitor.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		clonnitor.setCreated(LocalDateTime.now());
		clonnitor.setEnabled(false);

		clonnitorRepository.save(clonnitor);

		final String token = generateVerificationToken(clonnitor);
		mailService.sendMail(new NotificationEmail("Please Activate your Account",
				clonnitor.getEmail(), "Thank you for signing up to Spring Reddit, " +
				"please click on the below url to activate your account : " +
				"http://localhost:8080/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(Clonnitor clonnitor) {
		final String token = UUID.randomUUID().toString();
		final VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setClonnitor(clonnitor);

		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public void verifyAccount(String token) {
		final VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new ClonnitException("Invalid Token"));

		fetchUserandEnable(verificationToken);
	}

	@Transactional
	private void fetchUserandEnable(VerificationToken verificationToken) {
		final String username = verificationToken.getClonnitor().getUsername();
		final Clonnitor clonnitor = clonnitorRepository.findByUsername(username)
				.orElseThrow(() -> new ClonnitException("User not Found"));
		clonnitor.setEnabled(true);
		clonnitorRepository.save(clonnitor);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		final Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);

		return new AuthenticationResponse(token, loginRequest.getUsername());
	}
}