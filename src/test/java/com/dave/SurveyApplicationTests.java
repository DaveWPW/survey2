package com.dave;

import com.dave.dao.RoleDao;
import com.dave.dao.RoleMenuDao;
import com.dave.dao.UserDao;
import com.dave.entity.Role;
import com.dave.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyApplicationTests {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Test
    public void addUser() {
        User user = new User();
        user.setUsername("admin");
        user.setRealName("system admin");
        String salt = UUID.randomUUID().toString();
        SimpleHash sHash = new SimpleHash("MD5", "123456", salt);
        user.setPassword(sHash.toHex());
        user.setPasswordSalt(salt);
        user.setRoleId(1);
        user.setStaffId("20056254");
        user.setCreateUser("admin");
        user.setCreateTime(new Date());
        user.setModifyUser("admin");
        user.setModifyTime(new Date());
        user.setStatus(1);
        userDao.addUser(user);
    }

    @Test
    public void addRole(){
        Role role = new Role();
        role.setRoleName("admin");
        role.setRoleNote("超级管理员");
        role.setCreateUser("admin");
        role.setCreateTime(new Date());
        role.setModifyUser("admin");
        role.setModifyTime(new Date());
        role.setStatus(1);
        Integer[] menuIds = new Integer[]{1, 2, 3, 4, 5, 6};
        int roleId = roleDao.addRole(role);
        for(int menuId : menuIds) {
            roleMenuDao.addRoleMenu(roleId, menuId);
        }
    }

}
