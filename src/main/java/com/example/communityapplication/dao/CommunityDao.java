package com.example.communityapplication.dao;

import com.example.communityapplication.model.Community;

public interface CommunityDao {
    Community findByCommunityName(String name);

    void save(Community theCommunity);

}
