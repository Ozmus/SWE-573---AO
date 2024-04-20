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
	public ContentTemplate findByNameAndCommunityId(String name, Community community) {
		TypedQuery<ContentTemplate> theQuery = entityManager.createQuery("from ContentTemplate where name=:ctName and community=:ctCommunity", ContentTemplate.class);
		theQuery.setParameter("ctName", name);
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
}
