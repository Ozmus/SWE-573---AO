package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.UserRoleDao;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserRoleServiceImpl implements UserRoleService {

	private UserRoleDao userRoleDao;

	@Autowired
	public UserRoleServiceImpl(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Override
	public UserRole getRoleByUserAndCommunityId(UserRolesId userRolesId) {
		return userRoleDao.findByUserAndCommunityId(userRolesId);
	}

	@Override
	public void save(UserRole userRole) {
		userRoleDao.save(userRole);
	}
}
