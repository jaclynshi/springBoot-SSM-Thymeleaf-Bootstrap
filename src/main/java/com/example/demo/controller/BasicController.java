package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.bean.User;
import com.example.demo.model.dao.UserDAO;

// @RestController = @Controller + @ResponseBody
@RestController 
public class BasicController {
	
	@Autowired
	private UserDAO userDAO;
	
	@GetMapping(value = "")
	public ModelAndView index() {
		return new ModelAndView("pages/index"); // 姝ゅ鎸囧悜鐣岄潰
	}
	
	
	@GetMapping(value = "login.do")
	public Object login(String name, String password) {
		System.out.println("浼犲叆鍙傛暟锛歯ame=" + name + ", password=" + password);
		if (StringUtils.isEmpty(name)) {
			return "name涓嶈兘涓虹┖";
		} else if (StringUtils.isEmpty(password)) {
			return "password涓嶈兘涓虹┖";
		}
		User user = userDAO.find(name, password);
		if (user != null) {
			return user;
		} else {
			return "鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒";
		}
	}

}
