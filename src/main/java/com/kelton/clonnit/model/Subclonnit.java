package com.kelton.clonnit.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subclonnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Community name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;

	private Date createdDate = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clonnitor_id", referencedColumnName = "id")
	private Clonnitor creator;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Subclonnit that = (Subclonnit) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
