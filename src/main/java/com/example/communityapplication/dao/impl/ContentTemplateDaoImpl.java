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
	public ContentTemplate findById(int id) {
		TypedQuery<ContentTemplate> theQuery = entityManager.createQuery("from ContentTemplate where id=:ctId", ContentTemplate.class);
		theQuery.setParameter("ctId", id);


		ContentTemplate theContentTemplate = null;
		try {
			theContentTemplate = theQuery.getSingleResult();
		} catch (Exception e) {
			theContentTemplate = null;
		}

		return theContentTemplate;
	}

	@Override
	public ContentTemplate findByName(String name) {
		TypedQuery<ContentTemplate> theQuery = entityManager.createQuery("from ContentTemplate where name=:ctName", ContentTemplate.class);
		theQuery.setParameter("ctName", name);


		ContentTemplate theContentTemplate = null;
		try {
			theContentTemplate = theQuery.getSingleResult();
		} catch (Exception e) {
			theContentTemplate = null;
		}

		return theContentTemplate;
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
	public List<ContentTemplate> findByCommunityId(Community community) {
		TypedQuery<ContentTemplate> theQuery = entityManager.createQuery("from ContentTemplate where community =:ctCommunity", ContentTemplate.class);
		theQuery.setParameter("ctCommunity", community);

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
	public ContentTemplate save(ContentTemplate theContentTemplate) {
		return entityManager.merge(theContentTemplate);
	}

	@Override
	@Transactional
	public void delete(ContentTemplate theContentTemplate) {
		entityManager.remove(theContentTemplate);
	}
}
