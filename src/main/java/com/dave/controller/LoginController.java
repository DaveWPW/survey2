package com.dave.controller;

import com.dave.common.util.ShiroUtil;
import com.dave.common.vo.JsonResult;
import com.dave.entity.User;
import com.dave.service.MenuService;
import com.dave.service.UserService;
import org.apache.log4j.Logger;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Login控制层
 * 
 * @author Dave20191010
 * 
 */
@Controller
@RequestMapping("/")
public class LoginController {
	private static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
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
			logger.info(username+" 登录成功！！"+new Date());
			return new JsonResult("Login Succeed!", 1);
		} catch (UnknownAccountException e) {
			return new JsonResult("此用户不存在");
		} catch (IncorrectCredentialsException e) {
			return new JsonResult("密码错误");
		} catch (AuthenticationException e) {
			return new JsonResult("用户名密码错误");
		} catch (RuntimeException e) {
			logger.info("未知错误，请联系管理员");
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

	/**
	 * 获取页面显示级别标识
	 *
	 * @return
	 */
	@RequestMapping("doShowUI")
	@ResponseBody
    private JsonResult doShowUI(){
		User currentUser = ShiroUtil.getCurrentUser();
		List<String> level = menuService.findRoleMenuLevelById(currentUser.getRoleId());
		Map<String, Object> map = new HashMap<>();
		map.put("level", level);
		map.put("username", currentUser.getUsername());
		map.put("staffId", currentUser.getStaffId());
		map.put("roleName", currentUser.getRoleName());
		return new JsonResult(map);
	}
}