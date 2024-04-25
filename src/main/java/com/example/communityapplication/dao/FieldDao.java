package com.example.communityapplication.dao;

import com.example.communityapplication.model.ContentTemplate;
import com.example.communityapplication.model.Field;

import java.util.List;

public interface FieldDao {
    Field findByFieldId(long id);
    List<Field> findByContentTemplateId(ContentTemplate contentTemplate);
    void save(Field theField);

}
