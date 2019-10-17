package com.dave.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.common.util.ShiroUtil;
//import com.dave.common.vo.CheckBox;
import com.dave.common.vo.PageObject;
import com.dave.dao.RoleDao;
import com.dave.dao.RoleMenuDao;
import com.dave.entity.Role;
import com.dave.entity.User;
import com.dave.service.RoleService;

/**
 * Role业务层接口实现类
 * 
 * @author Dave20191012
 */
@Transactional(rollbackFor=Throwable.class)
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
    private RoleDao roleDao;
	@Autowired
    private RoleMenuDao roleMenuDao;

	@Override
	public int findRoleByRoleName(String roleName) {
		return roleDao.findRoleByRoleName(roleName);
	}

	@Override
	public int addRole(Role role) {
		User user = ShiroUtil.getCurrentUser();
		role.setCreateUser(user.getUsername());
		role.setCreateTime(new Date());
		role.setModifyUser(user.getUsername());
		role.setModifyTime(new Date());
		role.setStatus(1);
		int row = roleDao.addRole(role);
		for(int menuId : role.getMenuIds()) {
			roleMenuDao.addRoleMenu(role.getRoleId(), menuId);
		}
		return row;
	}

	@Override
	public PageObject<Role> findRoleList(int pageCurrent, String roleName) {
		int pageSize = 10;
        int startIndex = (pageCurrent-1) * pageSize;
        int rowCount = roleDao.getAllRoleCount();
        if(rowCount < startIndex){
            pageCurrent = 1;
            startIndex = (pageCurrent-1) * pageSize;
        }
        List<Role> records = roleDao.findRoleList(startIndex, pageSize, roleName);
        PageObject<Role> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
		return pageObject;
	}

	@Override
	public Map<String, Object> findRoleById(int roleId) {
		Role role = roleDao.findRoleById(roleId);
		if(role == null) {
			return null;
		}
		List<Integer> menuIds = roleMenuDao.findMenuIdsByRoleId(roleId);
		Map<String, Object> map = new HashMap<>();
		map.put("role", role);
		map.put("menuIds", menuIds);
		return map;
	}

	@Override
	public int updateRole(Role role) {
		User user = ShiroUtil.getCurrentUser();
		role.setModifyUser(user.getUsername());
		role.setModifyTime(new Date());
		role.setStatus(1);
		int row = roleDao.updateRole(role);
		roleMenuDao.deleteRoleMenu(role.getRoleId());
		for(int menuId : role.getMenuIds()) {
			roleMenuDao.addRoleMenu(role.getRoleId(), menuId);
		}
		return row;
	}
	
	@Override
	public String deleteRole(int roleId) {
		List<String> users = roleDao.findRoleUse(roleId);
		if(users.size() == 0) {
			User user = ShiroUtil.getCurrentUser();
			int row = roleDao.deleteRole(user.getUsername(), roleId);
			if(row != 1) {
				return "Delete Failed!!";
			}
		} else {
			return users.toString();
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findRoles() {
		return roleDao.findRoles();
	}

}
