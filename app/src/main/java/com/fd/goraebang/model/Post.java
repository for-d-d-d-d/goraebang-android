package com.fd.goraebang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Serializable {
	@JsonProperty("id")
	protected int id;
	@JsonProperty("user")
	protected User user;
	@JsonProperty("product")
	protected int productId;
	@JsonProperty("subject")
	protected String subject;
	@JsonProperty("content")
	protected String content;
	@JsonProperty("created")
	protected String created;
	@JsonProperty("cnt_hit")
	protected int cntHit;
	@JsonProperty("cnt_comment")
	protected int cntComment;
	@JsonProperty("cnt_good")
	protected int cntGood;
	@JsonProperty("image")
	private String image;
	@JsonProperty("thumbnail")
	private String thumbnail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public int getCntHit() {
		return cntHit;
	}

	public void setCntHit(int cntHit) {
		this.cntHit = cntHit;
	}

	public int getCntComment() {
		return cntComment;
	}

	public void setCntComment(int cntComment) {
		this.cntComment = cntComment;
	}

	public int getCntGood() {
		return cntGood;
	}

	public void setCntGood(int cntGood) {
		this.cntGood = cntGood;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
