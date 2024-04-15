package com.example.communityapplication.service;

import com.example.communityapplication.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.communityapplication.model.User;
public interface UserService extends UserDetailsService {

	public User getByUserName(String userName);

	void save(WebUser webUser);

}
