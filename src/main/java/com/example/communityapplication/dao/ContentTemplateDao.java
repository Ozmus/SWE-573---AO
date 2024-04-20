package com.example.communityapplication.dao;

import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentTemplate;

import java.util.List;

public interface ContentTemplateDao {
    ContentTemplate findByContentTemplateId(String id, Community community);
    List<ContentTemplate> findByCommunityId(long communityId);
    void save(ContentTemplate theContentTemplate);

}
