package com.example.demo.model.bean;

import java.io.Serializable;
public class Followuser implements Serializable {
	
	private static final long serialVersionUID = -5611386225028407298L;
	
	private Integer id;
	private String followFrom;
	private String followTo;
	private Integer myFollowCount;
	private Integer followMeCount;

    // 省略get和set方法，大家自己设置即可
	public Integer getMyFollowCount() {
		return myFollowCount;
	}
	public void setMyFollowCount(Integer myFollowCount) {
		this.myFollowCount = myFollowCount;
	}
	public Integer getFollowMeCount() {
		return followMeCount;
	}
	public void setFollowMeCount(Integer followMeCount) {
		this.followMeCount = followMeCount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFollowFrom() {
		return followFrom;
	}
	public void setFollowFrom(String followFrom) {
		this.followFrom = followFrom;
	}
	public String getFollowTo() {
		return followTo;
	}
	public void setFollowTo(String followTo) {
		this.followTo = followTo;
	}
}