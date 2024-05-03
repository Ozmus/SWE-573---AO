package com.example.communityapplication.service;


import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;

import java.util.List;

public interface UserRoleService {
	UserRole getRoleByUserAndCommunityId(UserRolesId userRolesId);
	List<UserRole> getRoleByUser(User user);
	List<UserRole> getRoleByCommunityId(int communityId);
	boolean kickUser(Role currentUserRole, User userToDelete, Community community);
	void promoteUser(Role currentUserRole, User userToPromote, Community community);
	void depromoteUser(Role currentUserRole, User userToPromote, Community community);
	void save(UserRole userRole);
}
