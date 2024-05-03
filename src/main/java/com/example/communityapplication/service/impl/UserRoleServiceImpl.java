package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.UserRoleDao;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserRoleServiceImpl implements UserRoleService {

	private UserRoleDao userRoleDao;

	@Autowired
	public UserRoleServiceImpl(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Override
	public UserRole getRoleByUserAndCommunityId(UserRolesId userRolesId) {
		if (userRolesId == null) {
			throw new IllegalArgumentException("userRolesId must not be null");
		}
		return userRoleDao.findByUserAndCommunityId(userRolesId);
	}

	@Override
	public List<UserRole> getRoleByUser(User user) {
		if(user == null){
			throw new IllegalArgumentException("user is null");
		}
		return userRoleDao.findByUserId(user.getId());
	}

	@Override
	public List<UserRole> getRoleByCommunityId(int communityId) {
		return userRoleDao.findByCommunityId(communityId);
	}

	@Override
	public void save(UserRole userRole) {
		if(userRole == null){
			throw new IllegalArgumentException("UserRole cannot be null");
		}
		userRoleDao.save(userRole);
	}
}
