package com.example.communityapplication.service.impl;

import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.User;
import com.example.communityapplication.dao.UserDao;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.UserRoleService;
import com.example.communityapplication.service.UserService;
import com.example.communityapplication.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private UserRoleService userRoleService;

	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao, UserRoleService userRoleService, BCryptPasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.userRoleService = userRoleService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User getByUserId(int userId) {
		return userDao.findByUserid(userId);
	}

	@Override
	public User getByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	public List<User> getUsersForManagement(int communityId, User user) {
		List<UserRole> userRoles = userRoleService.getRoleByCommunityId(communityId);
		UserRole currentUserRole = userRoleService.getRoleByUserAndCommunityId(new UserRolesId(user.getId(), communityId));
		List<User> users = new ArrayList<>();
		for(UserRole userRole : userRoles){
			if(currentUserRole.getRole().equals(Role.MOD)){
				if(user.getId() != userRole.getId().getUserId() && userRole.getRole().equals(Role.MEMBER)){
					users.add(this.getByUserId(userRole.getId().getUserId()));
				}
			}
			else if (currentUserRole.getRole().equals(Role.OWNER)){
				if(user.getId() != userRole.getId().getUserId() && !userRole.getRole().equals(Role.OWNER)){
					users.add(this.getByUserId(userRole.getId().getUserId()));
				}
			}
		}
		return users;
	}

	@Override
	public void save(WebUser webUser) {
		User user = new User();

		// assign user details to the user object
		user.setUserName(webUser.getUserName());
		user.setPassword(passwordEncoder.encode(webUser.getPassword()));
		user.setFirstName(webUser.getFirstName());
		user.setLastName(webUser.getLastName());
		user.setEmail(webUser.getEmail());

		// save user in the database
		userDao.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				new ArrayList<>());
	}
}
