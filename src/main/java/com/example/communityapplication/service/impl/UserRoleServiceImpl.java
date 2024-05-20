package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.UserRoleDao;
import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.UserRoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserRoleServiceImpl implements UserRoleService {

	private UserRoleDao userRoleDao;

	@Autowired
	public UserRoleServiceImpl(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Override
	public UserRole getRoleByUserAndCommunityId(UserRolesId userRolesId) {
		if (userRolesId == null) {
			throw new IllegalArgumentException("userRolesId must not be null");
		}
		return userRoleDao.findByUserAndCommunityId(userRolesId);
	}

	@Override
	public List<UserRole> getRoleByUser(User user) {
		if(user == null){
			throw new IllegalArgumentException("user is null");
		}
		return userRoleDao.findByUserId(user.getId());
	}

	@Override
	public List<UserRole> getRoleByCommunityId(int communityId) {
		return userRoleDao.findByCommunityId(communityId);
	}

	@Override
	@Transactional
	public boolean kickUser(Role kickerRole, User user, Community community) {
		UserRole userRole = this.getRoleByUserAndCommunityId(new UserRolesId(user.getId(), community.getId()));
		if (kickerRole == null) {
			return false;
		} else if (kickerRole.equals(Role.MEMBER)) {
			return false;
		} else if (kickerRole.equals(Role.MOD) && (userRole.getRole().equals(Role.MOD) || userRole.getRole().equals(Role.OWNER))) {
			return false;
		} else {
			userRoleDao.deleteUserRole(userRole);
			return true;
		}
	}

	@Override
	public void promoteUser(Role currentUserRole, User userToPromote, Community community) {
		UserRole userToPromoteRole = this.getRoleByUserAndCommunityId(new UserRolesId(userToPromote.getId(), community.getId()));
		if( (currentUserRole.equals(Role.MOD) && userToPromoteRole.getRole().equals(Role.MEMBER))
		|| (currentUserRole.equals(Role.OWNER) && userToPromoteRole.getRole().equals(Role.MEMBER))
		){
			userRoleDao.updateUserRole(userToPromoteRole, Role.MOD);
		} else if (currentUserRole.equals(Role.OWNER) && userToPromoteRole.getRole().equals(Role.MOD)) {
			userRoleDao.updateUserRole(userToPromoteRole, Role.OWNER);
		}
	}

	@Override
	public void depromoteUser(Role currentUserRole, User userToPromote, Community community) {
		UserRole userToPromoteRole = this.getRoleByUserAndCommunityId(new UserRolesId(userToPromote.getId(), community.getId()));
		if(currentUserRole.equals(Role.OWNER) && userToPromoteRole.getRole().equals(Role.MOD)){
			userRoleDao.updateUserRole(userToPromoteRole, Role.MEMBER);
		}
	}

	@Override
	@Transactional
	public void leaveCommunity(User userLeaving, Community community) {
		UserRole userRole = this.getRoleByUserAndCommunityId(new UserRolesId(userLeaving.getId(), community.getId()));
		if (userRole == null) {
			throw new IllegalArgumentException("userRole must not be null");
		}
		else if (userRole.equals(Role.OWNER) && !isAnotherOwnerExist(community)){
			throw new IllegalArgumentException("Last owner cannot be deleted");
		}
		else {
			userRoleDao.deleteUserRole(userRole);
		}
	}

	@Override
	@Transactional
	public void save(UserRole userRole) {
		if(userRole == null){
			throw new IllegalArgumentException("UserRole cannot be null");
		}
		userRoleDao.save(userRole);
	}

	private boolean isAnotherOwnerExist(Community community){
		List<UserRole> userRoles = userRoleDao.findOwnersByCommunityId(community.getId());
		if(userRoles.size() > 1){
			return true;
		}
		return false;
	}
}
