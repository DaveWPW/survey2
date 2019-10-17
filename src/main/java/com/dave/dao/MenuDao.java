package com.dave.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dave.common.vo.Node;

/**
 * Menu持久层接口
 * 
 * @author Dave20191012
 *
 */
public interface MenuDao {
	
	@Select("select menu_id as id, menu_name as name, parent_id from su_menu where status = 1")
	List<Node> findMenuNodes();
	
	List<String> findRoleMenuLevelById(@Param("roleId") int roleId);
	
}