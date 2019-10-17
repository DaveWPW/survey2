package com.dave.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dave.entity.User;

/**
 * User持久层映射接口
 * 
 * @author Dave20191011
 * 
 */
public interface UserDao {
	
	@Select("select u.*, r.role_name from su_user u left join su_role r on u.role_id = r.role_id where u.status = 1 and u.username = #{username}")
	User findUserByUserName(@Param("username")String username);

	int addUser(User user);
	
	@Select("select count(*) from su_user where status = 1")
	int getAllUserCount();
	
	List<User> findUserList(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("username") String username, @Param("staffId") String staffId);
	
	@Select("select * from su_user where status = 1 and user_id = #{userId}")
	User findObjectById(@Param("userId") Integer userId);
	
	int updateUser(User user);
	
	@Update("update su_user set status = 0 where user_id = #{userId}")
	int deleteUser(@Param("userId") int userId);
	
	@Update("update su_user set password = #{password} where user_id = #{userId}")
	int updatePassword(@Param("userId") int userId, @Param("password") String password);
}