package com.kelton.clonnit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
public class Clonnitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clonnitorId;

	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Password is required")
	private String password;

	@Email
	@NotEmpty(message = "Email is required")
	private String email;

	private LocalDateTime created;
	private boolean enabled;

	public Clonnitor() {
	}

	public Clonnitor(Long clonnitorId, String username, String password, String email, LocalDateTime created, boolean enabled) {
		this.clonnitorId = clonnitorId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.created = created;
		this.enabled = enabled;
	}

	public Long getClonnitorId() {
		return clonnitorId;
	}

	public void setClonnitorId(Long clonnitorId) {
		this.clonnitorId = clonnitorId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Clonnitor clonnitor = (Clonnitor) o;

		return clonnitorId.equals(clonnitor.clonnitorId);
	}

	@Override
	public int hashCode() {
		return clonnitorId.hashCode();
	}
}