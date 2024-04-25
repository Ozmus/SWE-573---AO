package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.dao.ContentTemplateDao;
import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.enums.DataTypes;
import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.CommunityService;
import com.example.communityapplication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommunityServiceImpl implements CommunityService {

	private CommunityDao communityDao;
	private UserRoleService userRoleService;
	private ContentTemplateDao contentTemplateDao;
	private FieldDao fieldDao;


	@Autowired
	public CommunityServiceImpl(CommunityDao communityDao, UserRoleService userRoleService, ContentTemplateDao contentTemplateDao, FieldDao fieldDao) {
		this.communityDao = communityDao;
		this.userRoleService = userRoleService;
		this.contentTemplateDao = contentTemplateDao;
		this.fieldDao = fieldDao;
	}

	@Override
	public Community getByCommunityId(long id) {
		return this.communityDao.findByCommunityId(id);
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
	public List<Community> getAllCommunitiesByUser(User user) {
		List<Community> communities = new ArrayList<>();
		for(UserRole userRole : userRoleService.getRoleByUser(user)){
			communities.add(communityDao.findByCommunityId(userRole.getId().getCommunityId()));
		}
		return communities;
	}

	@Override
	public void createCommunity(Community theCommunity, User currentUser) {
		// save community
		communityDao.save(theCommunity);
		Community createdCommunity = this.getByCommunityName(theCommunity.getName());
		userRoleService.save(new UserRole(new UserRolesId(currentUser.getId(), createdCommunity.getId()), Role.OWNER));
		contentTemplateDao.save(new ContentTemplate("Default", createdCommunity));
		ContentTemplate createdContentTemplate = contentTemplateDao.findByNameAndCommunityId("Default", createdCommunity);
		fieldDao.save(new Field("Text", DataTypes.String.toString(), createdContentTemplate));
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
