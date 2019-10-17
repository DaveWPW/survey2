package com.dave.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * RoleMenu持久层接口
 * 
 * @author Dave20191012
 *
 */
public interface RoleMenuDao {
	
	@Insert("insert into su_role_menu (role_id, menu_id) values (#{roleId}, #{menuId})")
	int addRoleMenu(
            @Param("roleId") int roleId, @Param("menuId") int menuId);
	
	@Delete("delete from su_role_menu where role_id = #{roleId}")
	int deleteRoleMenu(@Param("roleId") int roleId);
	
	@Select("select menu_id from su_role_menu where role_id = #{roleId}")
	List<Integer> findMenuIdsByRoleId(int roleId);
	
}