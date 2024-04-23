package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.Content;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ContentDaoImpl implements ContentDao {

	private EntityManager entityManager;

	@Autowired
	public ContentDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public Content findByContentId(long id) {
		TypedQuery<Content> theQuery = entityManager.createQuery("from Content where id=:cId", Content.class);
		theQuery.setParameter("cId", id);

		Content theContent = null;
		try {
			theContent = theQuery.getSingleResult();
		} catch (Exception e) {
			theContent = null;
		}

		return theContent;
	}

	@Override
	public List<Content> findByContentTemplateId(long contentTemplateId) {
		TypedQuery<Content> theQuery = entityManager.createQuery("from Content  where contentTemplate =:ctId", Content.class);
		theQuery.setParameter("ctId", contentTemplateId);

		List<Content> theContents = null;
		try {
			theContents = theQuery.getResultList();
		} catch (Exception e) {
			theContents = null;
		}
		return theContents;
	}

	@Override
	@Transactional
	public Content save(Content theContent) {
		return entityManager.merge(theContent);
	}

}
