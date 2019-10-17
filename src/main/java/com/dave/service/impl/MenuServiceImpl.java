package com.dave.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.common.vo.Node;
import com.dave.dao.MenuDao;
import com.dave.service.MenuService;

/**
 * Menu业务层接口实现类
 * 
 * @author Dave20191012
 *
 */
@Transactional(rollbackFor=Throwable.class)
@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Node> findMenuNodes() {
		return menuDao.findMenuNodes();
	}

	@Override
	public List<String> findRoleMenuLevelById(int roleId) {
		return menuDao.findRoleMenuLevelById(roleId);
	}

}
