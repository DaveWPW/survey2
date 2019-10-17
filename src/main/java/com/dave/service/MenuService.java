package com.dave.service;

import java.util.List;

import com.dave.common.vo.Node;

/**
 * Menu业务层接口
 * 
 * @author Dave20191012
 *
 */
public interface MenuService {
	
	List<Node> findMenuNodes();
	
	List<String> findRoleMenuLevelById(int roleId);
	
}
