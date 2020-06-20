package com.example.demo.model.dao;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import com.example.demo.model.bean.User;

import java.util.List;

@Mapper
public interface UserDAO {
	
	public User find(@Param("account")String account, @Param("password")String password);
	
	public User findByAccount(@Param("account")String account);
	
	public User findByID(@Param("userID")String userID);
	
	public Integer create(@Param("account")String account, @Param("password")String password, @Param("name")String name, @Param("agent")Integer agent, @Param("phone")String phone, @Param("address")String address, @Param("image")String image);

	public List<User> findMyFollow(@Param("userID")String userID);
	
	public List<User> findAll();
	
	public Integer remove(@Param("userID")Integer userID);
	
	public Integer updatePassword(@Param("password")String password, @Param("userID")String userID);
	
	public List<User> findSearch(@Param("keyword")String keyword);
	// 注： CRTL+Shift+O，快捷导入所有import
}
