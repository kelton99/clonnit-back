package com.kelton.clonnit.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	@NotBlank(message = "Post Name cannot be empty or Null")
	private String postName;

	@Nullable
	private String url;

	@Nullable
	@Lob
	private String description;

	private Integer voteCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clonnitorId", referencedColumnName = "clonnitorId")
	private Clonnitor clonnitor;

	private LocalDateTime createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Subclonnit subclonnit;

	public Post() {
	}

	public Post(Long postId, String postName, @Nullable String url, @Nullable String description, Integer voteCount, Clonnitor clonnitor, LocalDateTime createdDate, Subclonnit subclonnit) {
		this.postId = postId;
		this.postName = postName;
		this.url = url;
		this.description = description;
		this.voteCount = voteCount;
		this.clonnitor = clonnitor;
		this.createdDate = createdDate;
		this.subclonnit = subclonnit;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Nullable
	public String getUrl() {
		return url;
	}

	public void setUrl(@Nullable String url) {
		this.url = url;
	}

	@Nullable
	public String getDescription() {
		return description;
	}

	public void setDescription(@Nullable String description) {
		this.description = description;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public Clonnitor getClonnitor() {
		return clonnitor;
	}

	public void setClonnitor(Clonnitor clonnitor) {
		this.clonnitor = clonnitor;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Subclonnit getSubclonnit() {
		return subclonnit;
	}

	public void setSubclonnit(Subclonnit subclonnit) {
		this.subclonnit = subclonnit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Post post = (Post) o;

		return postId.equals(post.postId);
	}

	@Override
	public int hashCode() {
		return postId.hashCode();
	}
}
