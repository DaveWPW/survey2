package com.dave.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.common.util.ShiroUtil;
import com.dave.common.vo.PageObject;
import com.dave.dao.UserDao;
import com.dave.entity.User;
import com.dave.service.UserService;

/**
 * User业务层接口实现类
 * 
 * @author Dave20191011
 */
@Transactional(rollbackFor=Throwable.class)
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	@Override
	public User findUserByUserName(String username){
		User user = userDao.findUserByUserName(username);
		return user;
	}
	
	@Override
	public int addUser(User user) {
		int rows = 0;
		String password = "12345678";
		String salt = UUID.randomUUID().toString();
		SimpleHash sHash = new SimpleHash("MD5", password, salt);
		user.setPassword(sHash.toHex());
		user.setPasswordSalt(salt);
		User createUser = ShiroUtil.getCurrentUser();
		user.setCreateUser(createUser.getUsername());
		user.setCreateTime(new Date());
		user.setModifyUser(createUser.getUsername());
		user.setModifyTime(new Date());
		user.setStatus(1);
		rows = userDao.addUser(user);
		return rows;
	}
	
	@Override
	public PageObject<User> findUserList(Integer pageCurrent, String username, String staffId){
		int pageSize = 10;
        int startIndex = (pageCurrent-1) * pageSize;
		int rowCount = userDao.getAllUserCount();
		if(rowCount < startIndex){
            pageCurrent = 1;
            startIndex = (pageCurrent-1) * pageSize;
        }
		List<User> records = userDao.findUserList(startIndex, pageSize, username, staffId);
		PageObject<User> pageObject = new PageObject<>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		return pageObject;
	}
	
	@Override
	public User findUserById(int userId) {
		User user = userDao.findObjectById(userId);
		return user;
	}
	
	@Override
	public int updateUser(User user) {
		int rows = 0;
		User userData = userDao.findUserByUserName(user.getUsername());	
		if(user.getIsRestPassword() == 1){
			String password = "12345678";
			SimpleHash sHash = new SimpleHash("MD5", password, userData.getPasswordSalt());
			user.setPassword(sHash.toHex());
		}
		User updateUser = ShiroUtil.getCurrentUser();
		user.setModifyUser(updateUser.getUsername());
		user.setModifyTime(new Date());
		rows = userDao.updateUser(user);
		return rows;
	}
	
	@Override
	public int deleteUser(int userId) {
		int rows = userDao.deleteUser(userId);
		return rows;
	}

	@Override
	public int updatePassword(int userId, String password) {
		return userDao.updatePassword(userId, password);
	}
	
}