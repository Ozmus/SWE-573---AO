package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.dao.FieldValueDao;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.Field;
import com.example.communityapplication.model.FieldValue;
import com.example.communityapplication.model.embedded.keys.FieldValueId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FieldValueDaoImpl implements FieldValueDao {

	private EntityManager entityManager;

	@Autowired
	public FieldValueDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public FieldValue findByFieldAndContentId(int fieldId, int contentId) {
		TypedQuery<FieldValue> theQuery = entityManager.createQuery("from FieldValue  where id =:fvId", FieldValue.class);
		theQuery.setParameter("fvId", new FieldValueId(fieldId, contentId));

		FieldValue theFieldValue = null;
		try {
			theFieldValue = theQuery.getSingleResult();
		} catch (Exception e) {
			theFieldValue = null;
		}
		return theFieldValue;	}

	@Override
	public List<FieldValue> findByContentId(Content content) {
		TypedQuery<FieldValue> theQuery = entityManager.createQuery("from FieldValue  where content =:fContent", FieldValue.class);
		theQuery.setParameter("fContent", content);

		List<FieldValue> theFieldValues = null;
		try {
			theFieldValues = theQuery.getResultList();
		} catch (Exception e) {
			theFieldValues = null;
		}
		return theFieldValues;
	}

	@Override
	public List<Content> search(String keyword) {
		TypedQuery<Content> query = entityManager.createQuery("SELECT fv.content FROM FieldValue fv WHERE fv.value LIKE :keyword", Content.class);
		query.setParameter("keyword", "%" + keyword + "%");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save(FieldValue theFieldValue) {
		entityManager.merge(theFieldValue);
	}
}
