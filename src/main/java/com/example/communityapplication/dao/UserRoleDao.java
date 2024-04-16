package com.example.communityapplication.dao;

import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;

public interface UserRoleDao {
    UserRole findByUserAndCommunityId(UserRolesId userRolesId);

    void save(UserRole theUserRole);

}
