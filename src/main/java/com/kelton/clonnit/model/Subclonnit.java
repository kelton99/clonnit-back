package com.kelton.clonnit.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Subclonnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Community name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Post> posts;

	private LocalDateTime createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Clonnitor clonnitor;

	public Subclonnit() {
	}

	public Subclonnit(Long id, String name, String description, List<Post> posts, LocalDateTime createdDate, Clonnitor clonnitor) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.posts = posts;
		this.createdDate = createdDate;
		this.clonnitor = clonnitor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Clonnitor getClonnitor() {
		return clonnitor;
	}

	public void setClonnitor(Clonnitor clonnitor) {
		this.clonnitor = clonnitor;
	}

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
