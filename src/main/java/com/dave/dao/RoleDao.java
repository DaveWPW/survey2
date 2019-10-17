package com.dave.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//import com.dave.common.vo.CheckBox;
import com.dave.entity.Role;

/**
 * Role持久层接口
 * 
 * @author Dave20191012
 *
 */
public interface RoleDao {

	@Select("select count(*) from su_role where status=1 and role_name=#{roleName}")
	int findRoleByRoleName(@Param("roleName")String roleName);

	int addRole(Role role);
	
	@Select("select count(*) from su_role where status=1")
	int getAllRoleCount();
	
	List<Role> findRoleList(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("roleName") String roleName);
	
	@Select("select * from su_role where status = 1 and role_id = #{roleId}")
	Role findRoleById(@Param("roleId") int roleId);
	
	int updateRole(Role role);
	
	@Select("select username from su_uesr where status = 1 and role_id = #{roleId}")
	List<String> findRoleUse(@Param("roleId") int roleId);
	
	@Update("update su_role set status = 0, modify_user = #{username}, modify_time = now() where role_id = #{roleId}")
	int deleteRole(@Param("username") String username, @Param("roleId") int roleId);
	
	@Select("select role_id, role_name from su_role where status=1")
	List<Map<String, Object>> findRoles();

}
