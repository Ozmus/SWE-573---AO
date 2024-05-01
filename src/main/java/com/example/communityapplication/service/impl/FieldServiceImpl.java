package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.dao.UserRoleDao;
import com.example.communityapplication.model.Field;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.ContentService;
import com.example.communityapplication.service.ContentTemplateService;
import com.example.communityapplication.service.FieldService;
import com.example.communityapplication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FieldServiceImpl implements FieldService {

	private FieldDao fieldDao;
	private ContentTemplateService contentTemplateService;

	@Autowired
	public FieldServiceImpl(FieldDao fieldDao, ContentTemplateService contentTemplateService) {
		this.fieldDao = fieldDao;
		this.contentTemplateService = contentTemplateService;
	}

	@Override
	public List<Field> getFieldsByContentTemplateId(int contentTemplateId) {
		return fieldDao.findByContentTemplateId(contentTemplateService.getById(contentTemplateId));
	}
}
