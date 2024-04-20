package com.example.communityapplication.dao;

import com.example.communityapplication.model.Field;

import java.util.List;

public interface FieldDao {
    Field findByFieldId(long id);
    List<Field> findByContentTemplateId(long contentTemplateId);
    void save(Field theField);

}
