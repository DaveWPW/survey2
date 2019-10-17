package com.dave.service;

import com.dave.common.vo.PageObject;
import com.dave.entity.User;
/**
 * User业务层接口
 * 
 * @author Dave20191011
 */
public interface UserService {
	
	User findUserByUserName(String username);
	
	int addUser(User user);
	
	PageObject<User> findUserList(Integer pageCurrent, String username, String staffId);
	
	User findUserById(int userId);
	
	int updateUser(User user);
	
	int deleteUser(int userId);
	
	int updatePassword(int userId, String password);
	
}