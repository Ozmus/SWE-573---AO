package com.example.communityapplication.service;


import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;

public interface UserRoleService {

	public UserRole getRoleByUserAndCommunityId(UserRolesId userRolesId);

	void save(UserRole userRole);

}
