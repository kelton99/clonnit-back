package com.kelton.clonnit.dto;

public class PostRequest {

    private Long postId;
    private String subclonnitName;
    private String postName;
    private String url;
    private String description;

    public PostRequest() { }

    public PostRequest(Long postId, String subclonnitName, String postName, String url, String description) {
        this.postId = postId;
        this.subclonnitName = subclonnitName;
        this.postName = postName;
        this.url = url;
        this.description = description;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getSubclonnitName() {
        return subclonnitName;
    }

    public void setSubclonnitName(String subclonnitName) {
        this.subclonnitName = subclonnitName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}