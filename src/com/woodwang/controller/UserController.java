package com.woodwang.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.woodwang.bean.User;
import com.woodwang.common.ControllerLogAnnotation;

/**
 * Controller
 * @author admin
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {
	
	private static Map<String,String> users = new HashMap<String,String>();
	
	//测试demo默认几个账号
	static {
		users.put("admin", "admin");
		users.put("test", "test");
		users.put("root", "root");
	}
	
	private static Logger logger = Logger.getLogger(UserController.class);

	/**
	 * add a new user
	 * @param user
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	@ControllerLogAnnotation
	public ModelAndView login(HttpServletRequest request,String userId,String password) {
		ModelAndView view = new ModelAndView();
		
		if(!users.containsKey(userId) || userId== null) {
			view.setViewName("index");
			logger.info("账号不存在");
		}else {
			String storedPwd = users.get(userId);
			if(storedPwd.equals(password)) {
				logger.info("账号OK");
				view.setViewName("main");
				
				HttpSession session = request.getSession(true);
				String sessionId = session.getId();
				User user = new User();
				user.setUserId(userId);
				session.setAttribute(sessionId, user);
			}else {
				logger.info("账号和密码不匹配");
				view.setViewName("index");
			}
		}
		
		return view;
	}
	
	/**
	 * add a new user
	 * @param user
	 * @return
	 */
	@RequestMapping("/addUser")
	@ResponseBody
	@ControllerLogAnnotation
	public ModelAndView addUser(String userId,String password) {
		ModelAndView view = new ModelAndView();
		view.setViewName("main");
		if(users.containsKey(userId)) {
			logger.info("账号已经存在，添加失败。");
		}else {
			users.put(userId, password);
			logger.info("账号不存在，添加成功。");
		}
		
		return view;
	}

}
