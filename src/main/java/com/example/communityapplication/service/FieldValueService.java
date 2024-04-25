package com.example.communityapplication.service;

import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentForm;
import com.example.communityapplication.model.FieldValue;
import java.util.List;

public interface FieldValueService {
	List<FieldValue> getFieldValuesByContent(Content content);
	void save(FieldValue fieldValue);
	void saveFieldValues(ContentForm contentForm, Content content);
}
