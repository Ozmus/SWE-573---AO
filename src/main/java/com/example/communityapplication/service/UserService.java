package com.example.communityapplication.service;

import com.example.communityapplication.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.communityapplication.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
	public User getByUserId(int userId);
	public User getByUserName(String userName);
	List<User> getUsersForManagement(int communityId, User user);
	void save(WebUser webUser);
}
