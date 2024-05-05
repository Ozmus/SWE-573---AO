package com.example.communityapplication.service;


import com.example.communityapplication.model.Field;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;

import java.util.List;

public interface FieldService {
	Field getFieldById(int fieldId);
	List<Field> getFieldsByContentTemplateId(int contentTemplateId);
	Field save(Field field);
	void delete(int field);
}
