package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommunityServiceImpl implements CommunityService {

	private CommunityDao communityDao;

	@Autowired
	public CommunityServiceImpl(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}

	@Override
	public Community getByCommunityName(String name) {
		return communityDao.findByCommunityName(name);
	}

	@Override
	public void save(Community community) {
		communityDao.save(community);
	}
}
