package com.dave.common.util;

import org.apache.shiro.SecurityUtils;

import com.dave.entity.User;

/**
 * Shiro工具类
 * 
 * @author Dave20191011
 *
 */
public class ShiroUtil {
	
	public static User getCurrentUser() {
		return (User)SecurityUtils.getSubject().getPrincipal();
	}
	
}
