package com.dave.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 角色PO
 * 
 * @author Dave20191012
 *
 */
public class Role implements Serializable{
	private static final long serialVersionUID = 4321440581678484361L;
	/**角色ID*/
	private Integer roleId;
	/**角色名称*/
	private String roleName;
	/**角色描述*/
	private String roleNote;
	/**创建用户*/
	private String createUser;
	/**创建时间*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**修改用户*/
	private String modifyUser;
	/**修改时间*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date modifyTime;
	/**用户状态 0：删除，1：使用， 9：禁用*/
	private int status;
	/**菜单ID数组*/
	private Integer[] menuIds;
	
	public void setRoleNote(String roleNote) {
		this.roleNote = roleNote;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleNote() {
		return roleNote;
	}
	public void setRoelNote(String roleNote) {
		this.roleNote = roleNote;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer[] getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(Integer[] menuIds) {
		this.menuIds = menuIds;
	}
}
