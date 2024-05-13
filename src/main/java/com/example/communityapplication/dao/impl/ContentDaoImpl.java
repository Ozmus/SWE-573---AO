package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.dao.ContentDao;
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
public class ContentDaoImpl implements ContentDao {

	private EntityManager entityManager;

	@Autowired
	public ContentDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public Content findByContentId(int id) {
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
	public List<Content> findByContentTemplateId(ContentTemplate contentTemplate) {
		TypedQuery<Content> theQuery = entityManager.createQuery("from Content  where contentTemplate =:ctContentTemplate", Content.class);
		theQuery.setParameter("ctContentTemplate", contentTemplate);

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

	@Override
	public List<Content> searchByTitle(String title) {
		TypedQuery<Content> query = entityManager.createQuery("SELECT c FROM Content c WHERE c.title LIKE :title", Content.class);
		query.setParameter("title", "%" + title + "%");
		return query.getResultList();
	}
}
