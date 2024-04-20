package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.dao.ContentTemplateDao;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentTemplate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ContentTemplateDaoImpl implements ContentTemplateDao {

	private EntityManager entityManager;

	@Autowired
	public ContentTemplateDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public ContentTemplate findByContentTemplateId(String id, Community community) {
		TypedQuery<ContentTemplate> theQuery = entityManager.createQuery("from ContentTemplate where name=:ctName and community=:ctCommunity", ContentTemplate.class);
		theQuery.setParameter("ctName", id);
		theQuery.setParameter("ctCommunity", community);


		ContentTemplate theContentTemplate = null;
		try {
			theContentTemplate = theQuery.getSingleResult();
		} catch (Exception e) {
			theContentTemplate = null;
		}

		return theContentTemplate;
	}

	@Override
	public List<ContentTemplate> findByCommunityId(long communityId) {
		TypedQuery<ContentTemplate> theQuery = entityManager.createQuery("from ContentTemplate where community =:ctId", ContentTemplate.class);
		theQuery.setParameter("ctId", communityId);

		List<ContentTemplate> theContentTemplates = null;
		try {
			theContentTemplates = theQuery.getResultList();
		} catch (Exception e) {
			theContentTemplates = null;
		}
		return theContentTemplates;
	}

	@Override
	@Transactional
	public void save(ContentTemplate theContentTemplate) {
		entityManager.merge(theContentTemplate);
	}

	/*
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
	public List<Content> findByCommunityId(long communityId) {
		TypedQuery<Content> theQuery = entityManager.createQuery("from Content", Content.class);

		List<Content> theContents = null;
		try {
			theContents = theQuery.getResultList();
		} catch (Exception e) {
			theContents = null;
		}
		return theContents;
	}

	@Override
	public void save(Content theContent) {
		entityManager.merge(theContent);
	}
	*/
}
