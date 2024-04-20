package com.example.communityapplication.dao;

import com.example.communityapplication.model.Content;

import java.util.List;

public interface ContentDao {
    Content findByContentId(long id);
    List<Content> findByContentTemplateId(long contentTemplateId);
    void save(Content theContent);

}
