package com.example.communityapplication.dao;

import com.example.communityapplication.model.Community;

import java.util.List;
public interface CommunityDao {
    Community findByCommunityId(long id);
    Community findByCommunityName(String name);
    List<Community> findAllCommunities();
    void save(Community theCommunity);
    List<Community> searchCommunities(String keyword);
}
