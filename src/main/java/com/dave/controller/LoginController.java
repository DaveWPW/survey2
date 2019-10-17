package com.dave.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dave.common.util.ShiroUtil;
import com.dave.common.vo.JsonResult;
import com.dave.entity.User;
import com.dave.service.UserService;
/**
 * Login控制层
 * 
 * @author Dave20191010
 * 
 */
@Controller
@RequestMapping("/")
public class LoginController {
	@Autowired
	private UserService userService;
	
	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username, String password) {
		if(StringUtils.isEmpty(username)) {
			return new JsonResult("登录名不能为空");
		}
		if(StringUtils.isEmpty(password)) {
			return new JsonResult("密码不能为空");
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			return new JsonResult("Login Succeed!", 1);
		} catch (UnknownAccountException e) {
			return new JsonResult("此用户不存在");
		} catch (IncorrectCredentialsException e) {
			return new JsonResult("密码错误");
		} catch (AuthenticationException e) {
			return new JsonResult("用户名密码错误");
		} catch (RuntimeException e) {
			return new JsonResult("未知错误，请联系管理员");
		}
	}
	
	/**
	 * 修改用户密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping("doUpdatePassword")
    @ResponseBody
    public JsonResult doUpdatePassword(String oldPassword, String newPassword){
		if(StringUtils.isEmpty(oldPassword)) {
			return new JsonResult("旧密码不能为空");
		}
		if(StringUtils.isEmpty(newPassword)) {
			return new JsonResult("新密码不能为空");
		}
		User user = ShiroUtil.getCurrentUser();
		SimpleHash oldHash = new SimpleHash("MD5", oldPassword, user.getPasswordSalt());
		String oldHex = oldHash.toHex();
		if(!oldHex.equals(user.getPassword())) {
			return new JsonResult("旧密码错误！！");
		}
		SimpleHash newHash = new SimpleHash("MD5", newPassword, user.getPasswordSalt());
		String newHex = newHash.toHex();
		if(newHex.equals(user.getPassword())) {
			return new JsonResult("新密码和旧密码不能相同！！");
		}
		int row = userService.updatePassword(user.getUserId(), newHex);
		if(row == 1) {
			return new JsonResult("Update Succeed!", row);
		}
		return new JsonResult("Update Failed!!");
    }
	
}