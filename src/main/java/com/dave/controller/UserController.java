package com.dave.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dave.common.util.ShiroUtil;
import com.dave.common.vo.JsonResult;
import com.dave.common.vo.PageObject;
import com.dave.entity.User;
import com.dave.service.UserService;
/**
 * User控制层
 * 
 * @author Dave20191011
 * 
 */
@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 用户管理页面
	 * 
	 * @return system/user_list
	 */
	@RequiresPermissions(value = {"advanced", "user"})
    @RequestMapping("doUserListUI")
	public String doUserListUI(){
		return "system/user_list";
	}
    
    /**
     * 用户编辑页面
     * 
     * @return system/user_edit
     */
    @RequestMapping("doUserEditUI")
	public String doUserEditUI(){
		return "system/user_edit";
	}
    
    /**
     * 添加用户
     * 
     * @param user
     * @return
     */
    @RequestMapping("doAddUser")
    @ResponseBody
    public JsonResult doAddUser(User user){
    	if(user == null) {
    		return new JsonResult("保存对象不能为空");
    	}
    	if(StringUtils.isEmpty(user.getUsername())) {
    		return new JsonResult("用户名称不能为空");
    	}
    	if(StringUtils.isEmpty(user.getRealName())) {
    		return new JsonResult("真实名不能为空");
    	}
    	if(StringUtils.isEmpty(user.getStaffId())) {
    		return new JsonResult("员工号不能为空");
    	}
    	if(StringUtils.isEmpty(user.getRoleId())) {
    		return new JsonResult("角色不能为空");
    	}
    	User userData = userService.findUserByUserName(user.getUsername());
    	if(userData != null){
    		return new JsonResult("用户名已存在");	
    	}
    	int rows = userService.addUser(user);
    	if(rows != 1) {
    		return new JsonResult("Add Failed!!");
    	}
    	return new JsonResult("Add Succeed!", rows);
    }
    
    /**
     * 查找查询所有用户
     * 
     * @param pageCurrent
     * @param username
     * @param staffId
     * @return
     */
    @RequestMapping("doFindUserList")
    @ResponseBody
    public JsonResult doFindUserList(Integer pageCurrent, String username, String staffId){
    	if(pageCurrent == null || pageCurrent <= 0) {
    		return new JsonResult("参数不合法");
    	}
    	PageObject<User> pageUser = userService.findUserList(pageCurrent, username, staffId);
    	return new JsonResult(pageUser);
    }
    
    /**
     * 根据用户ID获取用户
     * 
     * @param userId
     * @return
     */
    @RequestMapping("doFindUserById")
	@ResponseBody
    public JsonResult doFindUserById(Integer userId){
    	if(StringUtils.isEmpty(userId)) {
    		return new JsonResult("用户ID不能为空");
    	}
    	User user = userService.findUserById(userId);
    	return new JsonResult(user);
    }
    
    /**
     * 修改用户
     * 
     * @param user
     * @return
     */
    @RequestMapping("doUpdateUser")
    @ResponseBody
    public JsonResult doUpdateUser(User user){
    	if(user == null) {
    		return new JsonResult("保存对象不能为空");
    	}
    	String admin = "admin";
    	if(admin.equals(user.getUsername()) && user.getIsRestPassword() == 0) {
    		return new JsonResult("禁止修改admin用户！！");
    	}
    	if(admin.equals(user.getUsername()) && user.getRoleId() != 1) {
    		return new JsonResult("禁止修改admin用户！！");
    	}
    	if(StringUtils.isEmpty(user.getUserId())) {
    		return new JsonResult("用户ID不能为空");
    	}
    	if(StringUtils.isEmpty(user.getUsername())) {
    		return new JsonResult("用户名不能为空");
    	}
    	if(StringUtils.isEmpty(user.getStaffId())) {
    		return new JsonResult("员工号不能为空");
    	}
    	if(StringUtils.isEmpty(user.getRoleId())) {
    		return new JsonResult("角色ID不能为空");
    	}
    	int row = userService.updateUser(user);
    	if(row == 1) {
    		User currentUser = ShiroUtil.getCurrentUser();
    		String username = currentUser.getUsername();
    		if(username.equals(user.getUsername()) && user.getIsRestPassword() == 1) {
    			return new JsonResult("Update Succeed!", 2);
    		}
    		return new JsonResult("Update Succeed!", row);
    	}
    	return new JsonResult("Update Failed!!");
    }
    
    /**
     * 删除用户
     * 
     * @param userId
     * @return
     */
    @RequestMapping("doDeleteUser")
    @ResponseBody
    public JsonResult doDeleteUser(Integer userId){
    	if(StringUtils.isEmpty(userId)) {
    		return new JsonResult("用户ID不能为空");
    	}
    	if(userId == 1) {
    		return new JsonResult("禁止删除admin用户！！");
    	}
    	User user = (User)SecurityUtils.getSubject().getPrincipal();
    	int id = user.getUserId();
    	if(id == userId) {
    		return new JsonResult("不能删除自身用户");
    	}
    	int rows = userService.deleteUser(userId);
		if(rows != 1) {
			return new JsonResult("Delete Failed!!");
		}
		return new JsonResult("Delete Succeed!", rows);
    }
    
}