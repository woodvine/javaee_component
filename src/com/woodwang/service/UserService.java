package com.woodwang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodwang.bean.User;
import com.woodwang.dao.UserDao;

/**
 * 用户集合操作Service类
 * @author admin
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 判断用户是否存在
	 * @param userId
	 * @return
	 */
	public boolean isUserExist(String userId) {
		//根据userId属性查找
		User findOne = userDao.findOneByAttribute("userId", userId);
		
		return findOne!=null;
	}
	
	/**
	 * 添加新用户
	 * @param user
	 */
	public void addUser(User user) {
		userDao.insert(user);
	}
}
