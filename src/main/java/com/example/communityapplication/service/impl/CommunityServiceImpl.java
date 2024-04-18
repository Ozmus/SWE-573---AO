package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.CommunityService;
import com.example.communityapplication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommunityServiceImpl implements CommunityService {

	private CommunityDao communityDao;
	private UserRoleService userRoleService;


	@Autowired
	public CommunityServiceImpl(CommunityDao communityDao, UserRoleService userRoleService) {
		this.communityDao = communityDao;
		this.userRoleService = userRoleService;
	}

	@Override
	public Community getByCommunityName(String name) {
		return this.communityDao.findByCommunityName(name);
	}

	@Override
	public List<Community> getAllCommunities() {
		return this.communityDao.findAllCommunities();
	}

	@Override
	public void createCommunity(Community theCommunity, User currentUser) {
		// save community
		communityDao.save(theCommunity);
		Community createdCommunity = this.getByCommunityName(theCommunity.getName());
		userRoleService.save(new UserRole(new UserRolesId(currentUser.getId(), createdCommunity.getId()), Role.OWNER));
	}

	@Override
	public void joinCommunity(Community community, User currentUser) {
		userRoleService.save(new UserRole(new UserRolesId(currentUser.getId(), community.getId()), Role.MEMBER));
	}

	@Override
	public boolean isExist(String name) {
		Community existing = this.getByCommunityName(name);
		return existing != null;
	}

	@Override
	public boolean isMember(Community community, User currentUser) {
		return userRoleService.getRoleByUserAndCommunityId(new UserRolesId(currentUser.getId(), community.getId())) != null;
	}
}
