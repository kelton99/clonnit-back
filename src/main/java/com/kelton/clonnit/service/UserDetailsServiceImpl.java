package com.kelton.clonnit.service;

import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Clonnitor;
import com.kelton.clonnit.repository.ClonnitorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final ClonnitorRepository clonnitorRepository;

	public UserDetailsServiceImpl(ClonnitorRepository clonnitorRepository) {
		this.clonnitorRepository = clonnitorRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Clonnitor clonnitor = clonnitorRepository.findByUsername(username)
				.orElseThrow(() -> new ClonnitException("User not Found"));

			return new User(clonnitor.getUsername(), clonnitor.getPassword(), clonnitor.isEnabled(), true, true, true, getAuthorities("USER"));

	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority((role)));
	}

}