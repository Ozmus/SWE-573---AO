package com.example.communityapplication.service;

import com.example.communityapplication.model.Community;

public interface CommunityService {
    public Community getByCommunityName(String name);

    void save(Community community);
}
