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
		return fieldValueDao.findByContentId(content);
	}

	@Override
	public void save(FieldValue fieldValue) {
		fieldValueDao.save(fieldValue);
	}

	@Override
	public void saveFieldValues(ContentForm contentForm, Content content) {
		for(int fieldId : contentForm.getFieldValues().keySet()){
			this.save(new FieldValue(new FieldValueId(content.getId(), fieldId), contentForm.getFieldValue(fieldId)));
		}
	}
}
