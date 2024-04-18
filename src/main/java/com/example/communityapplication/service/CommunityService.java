package com.example.communityapplication.service;

import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.User;

import java.util.List;

public interface CommunityService {
    public Community getByCommunityName(String name);
    public List<Community> getAllCommunities();
    void createCommunity(Community community, User currentUser);
    void joinCommunity(Community community, User currentUser);
    boolean isExist(String name);
    boolean isMember(Community community, User currentUser);
}
