package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.dao.FieldValueDao;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.Field;
import com.example.communityapplication.model.FieldValue;
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
	public FieldValue findByFieldValueId(long id) {
		return null;
	}

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
	@Transactional
	public void save(FieldValue theFieldValue) {
		entityManager.merge(theFieldValue);
	}
}
