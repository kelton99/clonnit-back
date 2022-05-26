package com.kelton.clonnit.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Post Name cannot be empty or Null")
	private String postName;

	@Nullable
	private String url;

	@Nullable
	@Lob
	private String description;

	@OneToMany
	private List<Comment> comments = new ArrayList<>();

	private Integer voteCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clonnitor_id", referencedColumnName = "id")
	private Clonnitor clonnitor;

	private Date createdDate = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subclonnit_id", referencedColumnName = "id")
	private Subclonnit subclonnit;

	public Post() {
	}

	public Post(Long id, String postName, @Nullable String url, @Nullable String description, List<Comment> comments, Integer voteCount, Clonnitor clonnitor, Date createdDate, Subclonnit subclonnit) {
		this.id = id;
		this.postName = postName;
		this.url = url;
		this.description = description;
		this.comments = comments;
		this.voteCount = voteCount;
		this.clonnitor = clonnitor;
		this.createdDate = createdDate;
		this.subclonnit = subclonnit;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
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

		return id.equals(post.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
