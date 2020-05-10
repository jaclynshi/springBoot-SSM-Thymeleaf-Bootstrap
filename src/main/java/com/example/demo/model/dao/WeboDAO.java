package com.example.demo.model.dao;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import com.example.demo.model.bean.Webo;

import java.util.List;

@Mapper
public interface WeboDAO {
	
	public List<Webo> findByUserID(@Param("userID")String userID);

	public Integer create(@Param("userID")String userID, @Param("content")String content, @Param("image")String image, @Param("publishTime")String publishTime);

	// 注： CRTL+Shift+O，快捷导入所有import
}
