package com.example.demo.model.dao;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import com.example.demo.model.bean.Webo;

import java.util.List;

@Mapper
public interface WeboDAO {
	
	public List<Webo> findByUserID(@Param("userID")String userID);
	
	public Webo findByID(@Param("weboID")Integer weboID);
	
	public List<Webo> find();

	public Integer create(@Param("userID")String userID, @Param("content")String content, @Param("image")String image, @Param("publishTime")String publishTime);
	
	public Integer deleteByID(@Param("myWeboID")Integer myWeboID);
	
	public Integer updateCommentCount(@Param("weboID")Integer weboID);
	
	public List<Webo> findFriendWebo(@Param("userID")String userID);

	public List<Webo> findSearch(@Param("keyword")String keyword);
	// 注： CRTL+Shift+O，快捷导入所有import
}
