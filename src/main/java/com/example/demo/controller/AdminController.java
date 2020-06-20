package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.bean.User;
import com.example.demo.model.dao.UserDAO;
import com.example.demo.model.bean.Webo;
import com.example.demo.model.dao.WeboDAO;
import com.example.demo.model.bean.Comment;
import com.example.demo.model.dao.CommentDAO;
import com.example.demo.model.bean.Followuser;
import com.example.demo.model.dao.FollowuserDAO;
import com.example.demo.fileupload.demo.util.FileUtils;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.List;

// @RestController = @Controller + @ResponseBody
@RestController 
public class AdminController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private WeboDAO weboDAO;
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private FollowuserDAO followuserDAO;
	
	@RequestMapping(value = "admin/login")
	public Object login(HttpSession session) {

		return new ModelAndView("admin/login"); // 姝ゅ鎸囧悜鐣岄潰;
	}
	
	@RequestMapping(value = "admin/login", method = RequestMethod.POST)
	public Object login1(Map<String, Object> map,@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "password", required = false) String password, HttpSession session) {
		session.removeAttribute("userID");
		session.removeAttribute("username");
		if(StringUtils.isEmpty(account)) {
			map.put("errors", "账号不能为空！");
		}else if(StringUtils.isEmpty(password)){
			map.put("errors", "密码不能为空!");
		}else {
			User user = userDAO.find(account, password);
			System.out.println(user);
			if (user != null && user.getPermission()==1) {
				session.setAttribute("userID", user.getId());
				session.setAttribute("username", user.getName());
				return new ModelAndView("redirect:/admin/userManager");
			} else {
				map.put("errors", "账户或者密码错误！");
			}
		}
			
		return new ModelAndView("admin/login");
	}
	
	@GetMapping(value = "admin/userManager")
	public Object userManager(Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("userID"))) {
			User user = userDAO.findByID(session.getAttribute("userID").toString());
//			return user.getPermission();
			if(user.getPermission()==1) {
				map.put("userID", session.getAttribute("userID"));
				map.put("username", session.getAttribute("username"));
				List<User> allUser = userDAO.findAll();
//					return allUser;
				if(allUser != null) {
//						return webo;
					map.put("allUser", allUser);
				}
			}else {
				return new ModelAndView("redirect:/");
			}
		}else {
			return new ModelAndView("redirect:/");
		}
	
		return new ModelAndView("admin/userManager");
	}
	
//	@RequestMapping(value = "admin/userManager", method = RequestMethod.POST)
//	public Object adminLogin(Map<String, Object> map,@RequestParam(value = "account", required = false) String account,
//			@RequestParam(value = "password", required = false) String password, HttpSession session) {
//		
//			if(StringUtils.isEmpty(account)) {
//				map.put("errors", "账号不能为空！");
//			}else if(StringUtils.isEmpty(password)){
//				map.put("errors", "密码不能为空!");
//			}else {
//				User user = userDAO.find(account, password);
////				return user.getPermission();
//				if (user != null) {
//					if(user.getPermission()==1) {
//						session.setAttribute("userID", user.getId());
//						session.setAttribute("username", user.getName());
//						map.put("username", session.getAttribute("username"));
//						map.put("userID", session.getAttribute("userID"));
//						List<User> allUser = userDAO.findAll();
////						return allUser;
//						if(allUser != null) {
////							return webo;
//							map.put("allUser", allUser);
//						}
//					}else {
//						map.put("errors", "该用户没有权限！");
//					}
//				} else {
//					map.put("errors", "账户或者密码错误！");
//				}
//			}
//			return new ModelAndView("admin/userManager");
//	}
	
	@RequestMapping(value = "admin/logout")
	public Object logout(HttpSession session) {
		session.removeAttribute("userID");
		session.removeAttribute("username");
		return new ModelAndView("redirect:/admin/login"); // 姝ゅ鎸囧悜鐣岄潰;
	}
	
	@GetMapping(value = "admin/removeUser")
	public Object removeUser(Integer userID, Map<String, Object> map, HttpSession session) {
//		return userID;
		userDAO.remove(userID);
		weboDAO.deleteByUserID(userID.toString());
		followuserDAO.deleteByUserID(userID);
		commentDAO.deleteByUserID(userID.toString());
		
		return new ModelAndView("redirect:/admin/userManager");
	}
	
	@RequestMapping(value = "admin/userManager", method = RequestMethod.POST)
	public Object savePassword(Map<String, Object> map,@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "userID", required = false) String userID, @RequestParam(value = "keyword", required = false) String keyword, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("userID"))) {
			map.put("username", session.getAttribute("username"));
			map.put("userID", session.getAttribute("userID"));
		
			if(!StringUtils.isEmpty(password) && !StringUtils.isEmpty(userID)) {
				userDAO.updatePassword(password, userID);
			}
			
			if(!StringUtils.isEmpty(keyword)) {
//				return keyword;
				List<User> allSearch = userDAO.findSearch(keyword);
//				return allSearch;
				if(allSearch != null) {
	//					return webo;
					map.put("allUser", allSearch);
				}
			}else {
				List<User> allUser = userDAO.findAll();
//				return allUser;
				if(allUser != null) {
	//					return webo;
					map.put("allUser", allUser);
				}
			}
		}
			
			return new ModelAndView("admin/userManager");
	}
	
	@GetMapping(value = "admin/weboManager")
	public Object weboManager(Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("userID"))) {
			User user = userDAO.findByID(session.getAttribute("userID").toString());
//			return user.getPermission();
			if(user.getPermission()==1) {
				map.put("userID", session.getAttribute("userID"));
				map.put("username", session.getAttribute("username"));
				List<Webo> allWebo = weboDAO.find();
//					return allUser;
				if(allWebo != null) {
//						return webo;
					map.put("allWebo", allWebo);
				}
			}else {
				return new ModelAndView("redirect:/");
			}
		}else {
			return new ModelAndView("redirect:/");
		}
	
		return new ModelAndView("admin/weboManager");
	}
	
	@RequestMapping(value = "admin/weboManager", method = RequestMethod.POST)
	public Object weboSearch(Map<String, Object> map, @RequestParam(value = "keyword", required = false) String keyword, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("userID"))) {
			map.put("username", session.getAttribute("username"));
			map.put("userID", session.getAttribute("userID"));
			
			if(!StringUtils.isEmpty(keyword)) {
//				return keyword;
				List<Webo> allSearch = weboDAO.findSearch(keyword);
//				return allSearch;
				if(allSearch != null) {
	//					return webo;
					map.put("allWebo", allSearch);
				}
			}else {
				List<Webo> allWebo = weboDAO.find();
//				return allUser;
				if(allWebo != null) {
	//					return webo;
					map.put("allWebo", allWebo);
				}
			}
		}
			
			return new ModelAndView("admin/weboManager");
	}
	
	@GetMapping(value = "admin/removeWebo")
	public Object removeWebo(Integer weboID, Map<String, Object> map, HttpSession session) {
//		return userID;
		weboDAO.deleteByID(weboID);
		return new ModelAndView("redirect:/admin/weboManager");
	}
}
