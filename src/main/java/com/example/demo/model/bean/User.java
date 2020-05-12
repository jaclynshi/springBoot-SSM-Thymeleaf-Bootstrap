package com.example.demo.model.bean;

import java.io.Serializable;
public class User implements Serializable {
	
	private static final long serialVersionUID = -5611386225028407298L;
	
	private Integer id;
	private String account;
	private String name;
	private String password;
	private Integer agent;
	private String image;
	private String phone;
	private String address;
	private Followuser followuser;

    // 省略get和set方法，大家自己设置即可
	public Followuser getFollowuser() {
		return followuser;
	}
	public void setFollowuser(Followuser followuser) {
		this.followuser = followuser;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAgent() {
		return agent;
	}
	public void setAgent(Integer agent) {
		this.agent = agent;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}