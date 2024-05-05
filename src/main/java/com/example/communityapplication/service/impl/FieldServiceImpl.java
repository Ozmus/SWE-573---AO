package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.ContentTemplateDao;
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
	private ContentTemplateDao contentTemplateDao;

	@Autowired
	public FieldServiceImpl(FieldDao fieldDao, ContentTemplateDao contentTemplateDao) {
		this.fieldDao = fieldDao;
		this.contentTemplateDao = contentTemplateDao;
	}

	@Override
	public Field getFieldById(int fieldId) {
		return fieldDao.findByFieldId(fieldId);
	}

	@Override
	public List<Field> getFieldsByContentTemplateId(int contentTemplateId) {
		ContentTemplate contentTemplate = contentTemplateDao.findById(contentTemplateId);
		if(contentTemplate != null) {
			return fieldDao.findByContentTemplateId(contentTemplate);
		}
		else{
			return new ArrayList<>();
		}
	}

	@Override
	public Field save(Field field) {
		return fieldDao.save(field);
	}

	@Override
	public void delete(int fieldId) {
		Field field = fieldDao.findByFieldId(fieldId);
		fieldDao.delete(field);
	}
}
