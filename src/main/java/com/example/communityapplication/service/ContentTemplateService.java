package com.example.communityapplication.service;

import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.ContentTemplate;
import com.example.communityapplication.model.User;

import java.util.List;

public interface ContentTemplateService {
    List<ContentTemplate> getByCommunity(Community community);
    ContentTemplate getById(int id);
    void deleteContentTemplateById(int id);
    ContentTemplate save(ContentTemplate contentTemplate);
}
