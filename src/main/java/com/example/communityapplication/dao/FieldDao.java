package com.example.communityapplication.dao;

import com.example.communityapplication.model.ContentTemplate;
import com.example.communityapplication.model.Field;

import java.util.List;

public interface FieldDao {
    Field findByFieldId(int id);
    List<Field> findByContentTemplateId(ContentTemplate contentTemplate);
    Field save(Field theField);
    void delete(Field theField);
}
