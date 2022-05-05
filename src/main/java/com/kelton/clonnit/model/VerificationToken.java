package com.kelton.clonnit.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;

	@OneToOne(fetch = FetchType.LAZY)
	private Clonnitor clonnitor;

	private LocalDateTime expiryDate;

	public VerificationToken() {
	}

	public VerificationToken(Long id, String token, Clonnitor clonnitor, LocalDateTime expiryDate) {
		this.id = id;
		this.token = token;
		this.clonnitor = clonnitor;
		this.expiryDate = expiryDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Clonnitor getClonnitor() {
		return clonnitor;
	}

	public void setClonnitor(Clonnitor clonnitor) {
		this.clonnitor = clonnitor;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		VerificationToken that = (VerificationToken) o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}