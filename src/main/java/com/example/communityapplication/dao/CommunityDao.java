package com.example.communityapplication.dao;

import com.example.communityapplication.model.Community;

import java.util.List;
public interface CommunityDao {
    Community findByCommunityName(String name);
    List<Community> findAllCommunities();
    void save(Community theCommunity);

}
