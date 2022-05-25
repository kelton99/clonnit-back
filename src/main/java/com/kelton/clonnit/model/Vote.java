package com.kelton.clonnit.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long voteId;

	private VoteType voteType;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "postId")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clonnitorId", referencedColumnName = "clonnitorId")
	private Clonnitor clonnitor;

	public Vote() { }

	public Vote(Long voteId, VoteType voteType, Post post, Clonnitor clonnitor) {
		this.voteId = voteId;
		this.voteType = voteType;
		this.post = post;
		this.clonnitor = clonnitor;
	}

	public Long getVoteId() {
		return voteId;
	}

	public void setVoteId(Long voteId) {
		this.voteId = voteId;
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

		return voteId.equals(vote.voteId);
	}

	@Override
	public int hashCode() {
		return voteId.hashCode();
	}
}