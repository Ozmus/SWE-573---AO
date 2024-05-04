package com.example.communityapplication.dao;

import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;

import java.util.List;

public interface UserRoleDao {
    UserRole findByUserAndCommunityId(UserRolesId userRolesId);
    List<UserRole> findByUserId(long userId);
    List<UserRole> findByCommunityId(int communityId);
    void updateUserRole(UserRole userRole, Role updatedUserRole);
    void deleteUserRole(UserRole userRole);
    void save(UserRole theUserRole);
}
