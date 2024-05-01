package com.example.communityapplication.service;


import com.example.communityapplication.model.Field;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;

import java.util.List;

public interface FieldService {
	public List<Field> getFieldsByContentTemplateId(int contentTemplateId);
}
