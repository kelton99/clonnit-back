package com.kelton.clonnit.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private VoteType voteType;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", referencedColumnName = "id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clonnitor_id", referencedColumnName = "id")
	private Clonnitor clonnitor;

	public Vote() { }

	public Vote(Long id, VoteType voteType, Post post, Clonnitor clonnitor) {
		this.id = id;
		this.voteType = voteType;
		this.post = post;
		this.clonnitor = clonnitor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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

		Vote vote = (Vote) o;

		return id.equals(vote.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}