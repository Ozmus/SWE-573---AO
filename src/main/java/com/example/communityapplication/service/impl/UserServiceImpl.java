package com.example.communityapplication.service.impl;

import com.example.communityapplication.model.User;
import com.example.communityapplication.dao.UserDao;
import com.example.communityapplication.service.UserService;
import com.example.communityapplication.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User getByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
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
