package com.dave.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dave.common.vo.JsonResult;
import com.dave.entity.Role;
import com.dave.service.RoleService;

/**
 * Role控制层
 * 
 * @author Dave20191012
 *
 */
@Controller
@RequestMapping("/role/")
public class RoleController {
	@Autowired
    private RoleService roleService;
	
	/**
	 * 角色管理页面
	 * 
	 * @return system/role_list
	 */
	@RequiresPermissions(value = {"advanced", "role"})
	@RequestMapping("doRoleListUI")
	public String doRoleListUI() {
		return "system/role_list";
	}
	
	/**
     * 角色编辑页面
     * 
     * @return system/role_edit
     */
    @RequestMapping("doRoleEditUI")
	public String doRoleEditUI(){
		return "system/role_edit";
	}
    
    /**
     * 添加角色
     * 
     * @param role
     * @return
     */
    @RequestMapping("doAddRole")
    @ResponseBody
    public JsonResult doAddRole(Role role) {
    	if(role == null) {
    		return new JsonResult("保存对象不能为空");
    	}
    	if(StringUtils.isEmpty(role.getRoleName())) {
    		return new JsonResult("角色名称不能为空");
    	}
    	if(StringUtils.isEmpty(role.getMenuIds()) || role.getMenuIds().length <= 0) {
    		return new JsonResult("菜单不能为空");
    	}
    	int count = roleService.findRoleByRoleName(role.getRoleName());
    	if(count > 0) {
    		return new JsonResult("该角色名称已经存在！");
    	}
    	int row = roleService.addRole(role);
    	if(row == 1) {
    		return new JsonResult("Add Succeed!", row); 		
    	}
    	return new JsonResult("Add Failed!!");
    }
    
    /**
     * 查找查询所有角色
     * 
     * @return
     */
    @RequestMapping("doFindRoleList")
    @ResponseBody
    public JsonResult doFindRoleList(Integer pageCurrent, String roleName) {
    	if(pageCurrent == null || pageCurrent <= 0) {
    		return new JsonResult("当前页面数不能为空");
    	}
    	return new JsonResult(roleService.findRoleList(pageCurrent, roleName));
    }
    
    /**
     * 根据角色ID获取角色
     * 
     * @param roleId
     * @return
     */
    @RequestMapping("doFindRoleById")
	@ResponseBody
	public JsonResult doFindRoleById(Integer roleId){
    	if(StringUtils.isEmpty(roleId)) {
    		return new JsonResult("角色ID不能为空");
    	}
    	Map<String, Object> map = roleService.findRoleById(roleId);
    	return new JsonResult(map);
    }
    
    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping("doUpdateRole")
    @ResponseBody
    public JsonResult doUpdateRole(Role role) {
    	if(role == null) {
    		return new JsonResult("保存对象不能为空");
    	}
    	String admin = "admin";
    	if(admin.equals(role.getRoleName())) {
    		return new JsonResult("禁止修改admin角色！！");
    	}
    	if(StringUtils.isEmpty(role.getRoleId())) {
    		return new JsonResult("角色ID不能为空");
    	}
    	if(StringUtils.isEmpty(role.getRoleName())) {
    		return new JsonResult("角色名称不能为空");
    	}
    	if(StringUtils.isEmpty(role.getMenuIds()) || role.getMenuIds().length <= 0) {
    		return new JsonResult("菜单不能为空");
    	}
    	int row = roleService.updateRole(role);
    	if(row == 1) {
    		return new JsonResult("Update Succeed!", row); 		
    	}
    	return new JsonResult("Update Failed!!");
    }
    
    /**
     * 删除角色
     * 
     * @param roleId
     * @return
     */
    @RequestMapping("doDeleteRole")
	@ResponseBody
	public JsonResult doDeleteRole(Integer roleId){
    	if(StringUtils.isEmpty(roleId)) {
    		return new JsonResult("角色ID不能为空");
    	}
    	if(roleId == 1) {
    		return new JsonResult("禁止删除admin角色！！");
    	}
		String info = roleService.deleteRole(roleId);
		if(info != null) {
			if("Delete Failed!!".equals(info)) {
				return new JsonResult(info);
			} else {
				return new JsonResult(info+"已使用该角色, 拒绝删除！");
			}
		}
		return new JsonResult("Delete Succeed!", 1);
	}
    
    /**
     * 获取所有角色
     * 
     * @return
     */
    @RequestMapping("doFindRoles")
	@ResponseBody
	public JsonResult doFindRoles(){
		List<Map<String, Object>> list = roleService.findRoles();
	    return new JsonResult(list);
	}
    
}