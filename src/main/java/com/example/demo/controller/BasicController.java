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
public class BasicController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private WeboDAO weboDAO;
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private FollowuserDAO followuserDAO;
	
	@GetMapping(value = "")
	public Object index(Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
		}
		List<Webo> webo = weboDAO.find();
		
		if(webo != null) {
//			return webo;
			map.put("Webos", webo);
		}
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
					session.setAttribute("userID", user.getId());
					session.setAttribute("username", user.getName());
					map.put("username", session.getAttribute("username"));
					map.put("userID", session.getAttribute("userID"));
					List<Webo> webo = weboDAO.find();
					
					Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
					Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

					map.put("myFollow", myFollow);
					map.put("followMe", followMe);
					
					if(webo != null) {
//						return webo;
						map.put("Webos", webo);
					}
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
		session.removeAttribute("userID");
		session.removeAttribute("username");
		return new ModelAndView("redirect:/"); // 姝ゅ鎸囧悜鐣岄潰;
	}
	
	
	@GetMapping(value = "publishWebo")
	public Object publishWebo(Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
		}else {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("pages/publishWebo");
	}
	
	@RequestMapping(value = "publishWebo", method = RequestMethod.POST)
	public Object publish(Map<String, Object> map,@RequestParam(value = "weboContent", required = false) String weboContent,
			@RequestParam("weboImg") MultipartFile weboImg, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
		}else {
			return new ModelAndView("redirect:/");
		}
		
		String fileName = "";
		
		if(StringUtils.isEmpty(weboContent)) {
			map.put("uploadErrors", "心情内容不能为空！");
		}else if(!StringUtils.isEmpty(weboImg.getOriginalFilename())) {
			// 要上传的目标文件存放路径
	        String localPath = "E:\\demo\\src\\main\\resources\\static\\upload";
	        // 上传成功或者失败的提示
	        String msg = "";
	        
	        fileName = weboImg.getOriginalFilename();
	        
	        int unixSep = fileName.lastIndexOf('/');
			// Check for Windows-style path
			int winSep = fileName.lastIndexOf('\\');
			// Cut off at latest possible point
			int pos = (winSep > unixSep ? winSep : unixSep);
			if (pos != -1)  {
				// Any sort of path separator found...
				fileName = fileName.substring(pos + 1);
			}
			
//			return fileName;

	        if (FileUtils.upload(weboImg, localPath, fileName)){
	            // 上传成功，给出页面提示
	            msg = "上传成功！";
	        }else {
	            msg = "上传失败！";
	        }
			
	        // 显示图片
	        map.put("msg", msg);
	        map.put("fileName", fileName);
		}
		
		Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
	    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	    
		weboDAO.create(session.getAttribute("userID").toString(), weboContent, fileName, sdf.format(d));
        
        return new ModelAndView("redirect:/myWebo");
	}
	
	
	@GetMapping(value = "myWebo")
	public Object myWebo(Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			
			List<Webo> webo = weboDAO.findByUserID(session.getAttribute("userID").toString());
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
			if(webo != null) {
//				return webo;
				map.put("myWebos", webo);
			}
		}else {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("pages/myWebo");
	}
	
	@GetMapping(value = "removeMyWebo")
	public Object removeMyWebo(Integer myWeboID) {
		if(myWeboID > 0) {
			weboDAO.deleteByID(myWeboID);
		}
		return new ModelAndView("redirect:/myWebo");
	}
	
	@GetMapping(value = "weboCenter")
	public Object weboCenter(Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			
			List<Webo> webo = weboDAO.find();
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
			
			if(webo != null) {
//				return webo;
				map.put("Webos", webo);
			}
		}else {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("pages/weboCenter");
	}
	
	@GetMapping(value = "viewWebo")
	public Object viewWebo(Integer weboID, Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
			if(weboID > 0) {
				Webo webo = weboDAO.findByID(weboID);
				List<Comment> comment = commentDAO.viewByID(weboID);
				Followuser follow = followuserDAO.find(weboID, session.getAttribute("userID").toString());
				if(follow != null) {
					map.put("follow", 1);
				}else {
					map.put("follow", 0);
				}
//				return follow;
	
//				return comment;
				map.put("comments", comment);
				map.put("webo", webo);
			}
		}else{
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("pages/viewWebo");
	}
	
	
	@RequestMapping(value = "viewWebo", method = RequestMethod.POST)
	public Object sendComment(Integer weboID, Map<String, Object> map,@RequestParam(value = "comment", required = false) String comment, HttpSession session) {	
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
			if(StringUtils.isEmpty(comment)) {
				map.put("commentErrors", "评论不能为空！");
			}else {	
				Date d = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
			    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			    
				commentDAO.create(weboID, session.getAttribute("userID").toString(), comment, sdf.format(d));
				weboDAO.updateCommentCount(weboID);
			}
		}else {
			return new ModelAndView("redirect:/");
		}
						
		return new ModelAndView("redirect:/viewWebo?weboID="+weboID);
	}
	
	@GetMapping(value = "followuser")
	public Object followuser(String followFrom, String followTo,String weboID, Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			followuserDAO.create(followFrom, followTo);
		}else{
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/viewWebo?weboID="+weboID);
	}
	
	@GetMapping(value = "unfollowuser")
	public Object unfollowuser(String followFrom, String followTo,String weboID, Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			followuserDAO.delete(followFrom, followTo);
		}else{
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/viewWebo?weboID="+weboID);
	}
	
	@GetMapping(value = "friendWebo")
	public Object friendWebo(Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
			List<Webo> friendWebo = weboDAO.findFriendWebo(session.getAttribute("userID").toString());
			map.put("friendWebos", friendWebo);
		}else{
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("pages/friendWebo");
	}
	
	@GetMapping(value = "friendList")
	public Object friendList(Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			
			List<User> myFollowInfo = userDAO.findMyFollow(session.getAttribute("userID").toString());
				
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
			
			if(myFollowInfo != null) {
//				return webo;
				map.put("myFollowInfo", myFollowInfo);
			}
		}else {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("pages/friendList");
	}
	
	@GetMapping(value = "viewFriendWebo")
	public Object viewFriendWebo(Integer friendID, Map<String, Object> map, HttpSession session) {
		if(!StringUtils.isEmpty(session.getAttribute("username"))) {
			map.put("userID", session.getAttribute("userID"));
			map.put("username", session.getAttribute("username"));
			
			List<Webo> friendWebo = weboDAO.findByUserID(friendID.toString());
//			return friendWebo;
			Followuser myFollow = followuserDAO.findMyFollowCount(session.getAttribute("userID").toString());
			Followuser followMe = followuserDAO.findFollowMeCount(session.getAttribute("userID").toString());

			map.put("myFollow", myFollow);
			map.put("followMe", followMe);
			if(friendWebo != null) {
//				return webo;
				map.put("friendWebos", friendWebo);
			}
		}else {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("pages/viewFriendWebo");
	}
}
