package com.example.communityapplication.dao;

import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;

import java.util.List;

public interface UserRoleDao {
    UserRole findByUserAndCommunityId(UserRolesId userRolesId);
    List<UserRole> findByUserId(long userId);
    List<UserRole> findByCommunityId(int communityId);
    void save(UserRole theUserRole);
}
