package com.example.demo.model.dao;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import com.example.demo.model.bean.Comment;

import java.util.List;

@Mapper
public interface CommentDAO {
	
	public List<Comment> viewByID(@Param("weboID")Integer weboID);
	
	public Integer create(@Param("weboID")Integer weboID, @Param("commentFrom")String commentFrom, @Param("content")String content, @Param("commentTime")String commentTime);
	
	public Integer deleteByUserID(@Param("userID")String userID);
	// 注： CRTL+Shift+O，快捷导入所有import
}
