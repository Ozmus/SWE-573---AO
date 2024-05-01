package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.model.ContentTemplate;
import com.example.communityapplication.model.Field;
import com.example.communityapplication.service.ContentTemplateService;
import com.example.communityapplication.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
		ContentTemplate contentTemplate = contentTemplateService.getById(contentTemplateId);
		if(contentTemplate != null) {
			return fieldDao.findByContentTemplateId(contentTemplate);
		}
		else{
			return new ArrayList<>();
		}
	}
}
