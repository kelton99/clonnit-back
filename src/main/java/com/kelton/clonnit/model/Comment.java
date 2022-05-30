package com.kelton.clonnit.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String text;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", referencedColumnName = "id")
	private Post post;

	private Date createdDate = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clonnitor_id", referencedColumnName = "id")
	private Clonnitor clonnitor;

	public Comment() {
	}

	public Comment(Long id, String text, Post post, Date createdDate, Clonnitor clonnitor) {
		this.id = id;
		this.text = text;
		this.post = post;
		this.createdDate = createdDate;
		this.clonnitor = clonnitor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
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

		Comment comment = (Comment) o;

		return id.equals(comment.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}