package com.example.demo.model.bean;

import java.io.Serializable;
public class Comment implements Serializable {
	
	private static final long serialVersionUID = -5611386225028407298L;
	
	private Integer id;
	private Integer weboID;
	private String commentFrom;
	private String content;
	private String commentTime;
	private Webo webo;

    // 省略get和set方法，大家自己设置即可
	public Webo getWebo() {
		return webo;
	}
	public void setWebo(Webo webo) {
		this.webo = webo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getweboID() {
		return weboID;
	}
	public void setWeboID(Integer weboID) {
		this.weboID = weboID;
	}
	public String getCommentFrom() {
		return commentFrom;
	}
	public void setCommentFrom(String commentFrom) {
		this.commentFrom = commentFrom;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
}