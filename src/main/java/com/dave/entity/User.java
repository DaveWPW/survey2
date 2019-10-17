package com.dave.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户PO
 * 
 * @author Dave20191010
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = -3720062967489912501L;
	/**用户ID*/
	private Integer userId;
	/**角色ID*/
	private Integer roleId;
	/**用户名*/
	private String username;
	/**密码*/
	private String password;
	/**真实名*/
	private String realName;
	/**密码盐值*/
	private String passwordSalt;
	/**员工号*/
	private String staffId;
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
	private Integer status;
	/**角色名称*/
	private String roleName;
	/**重置密码0：不重置，1：重置*/
	private Integer isRestPassword;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getIsRestPassword() {
		return isRestPassword;
	}
	public void setIsRestPassword(Integer isRestPassword) {
		this.isRestPassword = isRestPassword;
	}
}