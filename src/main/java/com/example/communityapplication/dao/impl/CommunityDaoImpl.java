package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.Content;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CommunityDaoImpl implements CommunityDao {

	private EntityManager entityManager;

	@Autowired
	public CommunityDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public Community findByCommunityId(long id) {
		TypedQuery<Community> theQuery = entityManager.createQuery("from Community where id=:cId", Community.class);
		theQuery.setParameter("cId", id);

		Community theCommunity = null;
		try {
			theCommunity = theQuery.getSingleResult();
		} catch (Exception e) {
			theCommunity = null;
		}

		return theCommunity;
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
	public List<Community> findAllCommunities() {
		TypedQuery<Community> theQuery = entityManager.createQuery("from Community", Community.class);

		List<Community> theCommunities = null;
		try {
			theCommunities = theQuery.getResultList();
		} catch (Exception e) {
			theCommunities = null;
		}
		return theCommunities;
	}

	@Override
	@Transactional
	public void save(Community theCommunity) {
		entityManager.merge(theCommunity);
	}

	@Override
	public List<Community> searchCommunities(String keyword) {
		TypedQuery<Community> query = entityManager.createQuery("SELECT c FROM Community c WHERE c.name LIKE :keyword OR c.description LIKE :keyword ", Community.class);
		query.setParameter("keyword", "%" + keyword + "%");
		return query.getResultList();
	}
}
