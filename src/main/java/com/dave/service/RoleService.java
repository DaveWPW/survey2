package com.dave.service;

import java.util.List;
import java.util.Map;

//import com.dave.common.vo.CheckBox;
import com.dave.common.vo.PageObject;
import com.dave.entity.Role;

/**
 * Role业务层接口
 * 
 * @author Dave20191012
 *
 */
public interface RoleService {

	int findRoleByRoleName(String roleName);

	int addRole(Role role);
	
	PageObject<Role> findRoleList(int pageCurrent, String roleName);
	
	Map<String, Object> findRoleById(int roleId);
	
	int updateRole(Role role);
	
	String deleteRole(int roleId);
	
	List<Map<String, Object>> findRoles();
	
}