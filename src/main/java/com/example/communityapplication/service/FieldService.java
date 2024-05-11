package com.example.communityapplication.service;


import com.example.communityapplication.model.Field;

import java.util.List;

public interface FieldService {
	Field getFieldById(int fieldId);
	List<Field> getFieldsByContentTemplateId(int contentTemplateId);
	Field save(Field field);
	void delete(int field);
}
