package com.example.communityapplication.service;

import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.Image;
import com.example.communityapplication.model.User;

import java.util.List;

public interface CommunityService {
    Community getByCommunityId(long id);
    Community getByCommunityName(String name);
    List<Community> getAllCommunities();
    List<Community> getAllCommunitiesByUser(User user);
    List<Community> getCommunitiesForManagement(User user);
    void createCommunity(Community community, User currentUser);
    void joinCommunity(Community community, User currentUser);
    boolean isExist(String name);
    boolean isMember(Community community, User currentUser);
    List<Community> searchCommunities(String keyword);
}
