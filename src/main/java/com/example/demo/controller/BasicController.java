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

// @RestController = @Controller + @ResponseBody
@RestController 
public class BasicController {
	
	@Autowired
	private UserDAO userDAO;
	
	@GetMapping(value = "")
	public Object index(Map<String, Object> map, HttpSession session) {
//		map.put("errors", "昂首千秋远,笑傲风间,堪寻敌手共论剑,高处不胜寒");
		map.put("userInfo", session.getAttribute("userInfo"));
		return new ModelAndView("pages/index"); // 姝ゅ鎸囧悜鐣岄潰
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object login(Map<String, Object> map,@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "password", required = false) String password, @RequestParam(value = "action", required = false) String action
			, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "agent", required = false) Integer agent
			, @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "address", required = false) String address, HttpSession session) {
		
		if(action.equals("login")) {
			if(StringUtils.isEmpty(account)) {
				map.put("errors", "账号不能为空！");
			}else if(StringUtils.isEmpty(password)){
				map.put("errors", "密码不能为空!");
			}else {
				User user = userDAO.find(account, password);
				System.out.println(user);
				if (user != null) {
					session.setAttribute("userInfo", user);
					map.put("userInfo", session.getAttribute("userInfo"));
				} else {
					map.put("errors", "账户或者密码错误！");
				}
			}
		}else if(action.equals("signup")) {
			if(StringUtils.isEmpty(account)) {
				map.put("errors", "账号不能为空！");
			}else if(StringUtils.isEmpty(password)){
				map.put("errors", "密码不能为空!");
			}else {
				User user = userDAO.findByAccount(account);
				if(user != null) {
					map.put("errors", "该用户已存在！");
				}else {
					userDAO.create(account, password, name, agent, phone, address);
					map.put("success", "注册成功，您可以进行登录！");
				}
			}
		}
				
		return new ModelAndView("pages/index");
	}
	
	@RequestMapping(value = "logout")
	public Object logout(HttpSession session) {
		session.removeAttribute("userInfo");
		return new ModelAndView("redirect:/"); // 姝ゅ鎸囧悜鐣岄潰;
	}
	
	
	@GetMapping(value = "publishWebo")
	public Object publishWebo(Map<String, Object> map, HttpSession session) {
		if(session.getAttribute("userInfo") != null) {
			map.put("userInfo", session.getAttribute("userInfo"));
		}else {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("pages/publishWebo");
	}
	
	@RequestMapping(value = "publishWebo", method = RequestMethod.POST)
	public Object publish(Map<String, Object> map,@RequestParam(value = "weboContent", required = false) String weboContent,
			@RequestParam("weboImg") MultipartFile weboImg, HttpSession session) {
		if(session.getAttribute("userInfo") != null) {
			map.put("userInfo", session.getAttribute("userInfo"));
		}else {
			return new ModelAndView("redirect:/");
		}
		
		if(StringUtils.isEmpty(weboContent)) {
			map.put("uploadErrors", "心情内容不能为空！");
		}
		
		if(!StringUtils.isEmpty(weboImg.getOriginalFilename())) {
			// 要上传的目标文件存放路径
	        String localPath = "E:/demo/src/main/resources/upload";
	        // 上传成功或者失败的提示
	        String msg = "";

	        if (FileUtils.upload(weboImg, localPath, weboImg.getOriginalFilename())){
	            // 上传成功，给出页面提示
	            msg = "上传成功！";
	        }else {
	            msg = "上传失败！";
	        }
	        
			
	        // 显示图片
	        map.put("msg", msg);
	        map.put("fileName", weboImg.getOriginalFilename());
		}
		
		Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
	    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		
//		Webo webo = WeboDAO.create(weboContent, weboImg.getOriginalFilename());
		
        
        return new ModelAndView("pages/publishWebo");
	}
}
