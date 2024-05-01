package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.FieldValueDao;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentForm;
import com.example.communityapplication.model.FieldValue;
import com.example.communityapplication.model.embedded.keys.FieldValueId;
import com.example.communityapplication.service.FieldValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldValueServiceImpl implements FieldValueService {

	private FieldValueDao fieldValueDao;

	@Autowired
	public FieldValueServiceImpl(FieldValueDao fieldValueDao) {
		this.fieldValueDao = fieldValueDao;
	}

	@Override
	public List<FieldValue> getFieldValuesByContent(Content content) {
		if(content == null) throw new IllegalArgumentException("Content cannot be null");
		return fieldValueDao.findByContentId(content);
	}

	@Override
	public void save(FieldValue fieldValue) {
		if(fieldValue == null) throw new IllegalArgumentException("FieldValue cannot be null");
		fieldValueDao.save(fieldValue);
	}

	@Override
	public void saveFieldValues(ContentForm contentForm, Content content) {
		if(content == null) {
			throw new IllegalArgumentException("Content is null");
		}
		else if(contentForm == null) {
			throw new IllegalArgumentException("ContentForm cannot be null");
		}
		else{
			for (int fieldId : contentForm.getFieldValues().keySet()) {
				this.save(new FieldValue(new FieldValueId(content.getId(), fieldId), contentForm.getFieldValue(fieldId)));
			}
		}
	}
}
