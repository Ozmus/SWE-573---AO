package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.dao.ContentTemplateDao;
import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.CommunityService;
import com.example.communityapplication.service.ContentTemplateService;
import com.example.communityapplication.service.FieldService;
import com.example.communityapplication.service.UserRoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContentTemplateServiceImpl implements ContentTemplateService {
	private ContentTemplateDao contentTemplateDao;
	private FieldService fieldService;

	@Autowired
	public ContentTemplateServiceImpl(ContentTemplateDao contentTemplateDao, FieldService fieldService) {
		this.contentTemplateDao = contentTemplateDao;
		this.fieldService = fieldService;
	}

	@Override
	public List<ContentTemplate> getByCommunity(Community community) {
		return this.contentTemplateDao.findByCommunityId(community);
	}

	@Override
	public ContentTemplate getById(int id) {
		return this.contentTemplateDao.findById(id);
	}

	@Override
	public void deleteContentTemplateById(int id) {
		ContentTemplate contentTemplate = this.contentTemplateDao.findById(id);
		List<Field> fields = fieldService.getFieldsByContentTemplateId(id);
		for (Field field : fields) {
			fieldService.delete(field.getId());
		}
		contentTemplateDao.delete(contentTemplate);
	}

	@Override
	public ContentTemplate save(ContentTemplate contentTemplate) {
		return this.contentTemplateDao.save(contentTemplate);
	}
}
