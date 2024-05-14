package com.example.communityapplication.dao;

import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.Field;
import com.example.communityapplication.model.FieldValue;

import java.util.List;

public interface FieldValueDao {
    FieldValue findByFieldValueId(long id);
    List<FieldValue> findByContentId(Content content);
    List<Content> search(String keyword);
    void save(FieldValue theFieldValue);
}
