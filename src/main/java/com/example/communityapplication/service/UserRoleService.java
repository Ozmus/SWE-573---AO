package com.example.communityapplication.service;


import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;

import java.util.List;

public interface UserRoleService {

	public UserRole getRoleByUserAndCommunityId(UserRolesId userRolesId);
	public List<UserRole> getRoleByUser(User user);
	void save(UserRole userRole);

}
