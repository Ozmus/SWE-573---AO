package com.example.communityapplication.dao;

import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentTemplate;
import com.example.communityapplication.model.User;

import java.util.List;

public interface ContentDao {
    Content findByContentId(int id);
    List<Content> findByContentTemplateId(ContentTemplate contentTemplateId);
    List<Content> findByUser(User user);
    Content save(Content theContent);
    List<Content> searchByTitle(String title);
}
