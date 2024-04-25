package com.example.communityapplication.service;

import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentForm;
import com.example.communityapplication.model.FieldValue;

public interface FieldValueService {
	void save(FieldValue fieldValue);
	void saveFieldValues(ContentForm contentForm, Content content);
}
