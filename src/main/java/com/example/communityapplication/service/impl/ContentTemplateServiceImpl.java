package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.dao.ContentTemplateDao;
import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.CommunityService;
import com.example.communityapplication.service.ContentTemplateService;
import com.example.communityapplication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContentTemplateServiceImpl implements ContentTemplateService {
	private ContentTemplateDao contentTemplateDao;


	@Autowired
	public ContentTemplateServiceImpl(ContentTemplateDao contentTemplateDao) {
		this.contentTemplateDao = contentTemplateDao;
	}

	@Override
	public List<ContentTemplate> getByCommunity(Community community) {
		return this.contentTemplateDao.findByCommunityId(community);
	}

	@Override
	public ContentTemplate getById(Long id) {
		return this.contentTemplateDao.findById(id);
	}
}
