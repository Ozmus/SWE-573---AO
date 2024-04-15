package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.model.Community;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CommunityDaoImpl implements CommunityDao {

	private EntityManager entityManager;

	@Autowired
	public CommunityDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public Community findByCommunityName(String theCommunityName) {

		TypedQuery<Community> theQuery = entityManager.createQuery("from Community where name=:cName", Community.class);
		theQuery.setParameter("cName", theCommunityName);

		Community theCommunity = null;
		try {
			theCommunity = theQuery.getSingleResult();
		} catch (Exception e) {
			theCommunity = null;
		}

		return theCommunity;
	}

	@Override
	@Transactional
	public void save(Community theCommunity) {
		entityManager.merge(theCommunity);
	}


}
