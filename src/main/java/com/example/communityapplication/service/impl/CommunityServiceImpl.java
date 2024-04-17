package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommunityServiceImpl implements CommunityService {

	private CommunityDao communityDao;

	@Autowired
	public CommunityServiceImpl(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}

	@Override
	public Community getByCommunityName(String name) {
		return this.communityDao.findByCommunityName(name);
	}

	@Override
	public List<Community> getAllCommunities() {
		return this.communityDao.findAllCommunities();
	}

	@Override
	public void save(Community community) {
		this.communityDao.save(community);
	}
}
