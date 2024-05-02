package com.example.communityapplication.dao;


import com.example.communityapplication.model.User;

public interface UserDao {

    User findByUserid(int userId);

    User findByUserName(String userName);

    void save(User theUser);
}
