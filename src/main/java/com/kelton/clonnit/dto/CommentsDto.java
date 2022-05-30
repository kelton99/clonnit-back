package com.kelton.clonnit.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class CommentsDto {

    private Long id;
    private Long postId;
    private Date createdDate = new Date();
    private String text;
    private String username;

    public CommentsDto() {
    }

    public CommentsDto(Long id, Long postId, Date createdDate, String text, String username) {
        this.id = id;
        this.postId = postId;
        this.createdDate = createdDate;
        this.text = text;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}