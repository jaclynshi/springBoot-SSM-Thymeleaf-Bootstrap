package com.example.demo.model.dao;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import com.example.demo.model.bean.Followuser;

@Mapper
public interface FollowuserDAO {
	
	public Followuser find(@Param("weboID")Integer weboID, @Param("userID")String userID);
	
	public Integer create(@Param("followFrom")String followFrom, @Param("followTo")String followTo);
	
	public Integer delete(@Param("followFrom")String followFrom, @Param("followTo")String followTo);

	// 注： CRTL+Shift+O，快捷导入所有import
}
