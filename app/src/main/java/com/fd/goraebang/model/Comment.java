package com.fd.goraebang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Serializable {
    @JsonProperty("id")
    private int commentId;
	@JsonProperty("user")
	private User user;
	@JsonProperty("post")
	private int post;
	@JsonProperty("content")
	private String content;
	@JsonProperty("created")
	private String created;
	@JsonProperty("image")
	private String image;
	@JsonProperty("cnt_good")
	private int cntGood;
    @JsonProperty("is_good")
    private boolean isGood;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getCntGood() {
		return cntGood;
	}

	public void setCntGood(int cntGood) {
		this.cntGood = cntGood;
	}

	public boolean isGood() {
		return isGood;
	}

	public void setGood(boolean good) {
		isGood = good;
	}
}
