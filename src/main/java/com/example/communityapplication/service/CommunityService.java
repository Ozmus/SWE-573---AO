package com.example.communityapplication.service;

import com.example.communityapplication.model.Community;

import java.util.List;

public interface CommunityService {
    public Community getByCommunityName(String name);
    public List<Community> getAllCommunities();
    void save(Community community);
}
