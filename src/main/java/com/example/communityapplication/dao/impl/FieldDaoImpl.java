package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.Field;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FieldDaoImpl implements FieldDao {

	private EntityManager entityManager;

	@Autowired
	public FieldDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public Field findByFieldId(long id) {
		TypedQuery<Field> theQuery = entityManager.createQuery("from Field where id=:fId", Field.class);
		theQuery.setParameter("fId", id);

		Field theField = null;
		try {
			theField = theQuery.getSingleResult();
		} catch (Exception e) {
			theField = null;
		}

		return theField;
	}

	@Override
	public List<Field> findByContentTemplateId(long contentTemplateId) {
		TypedQuery<Field> theQuery = entityManager.createQuery("from Field  where contentTemplate =:ctId", Field.class);
		theQuery.setParameter("ctId", contentTemplateId);

		List<Field> theFields = null;
		try {
			theFields = theQuery.getResultList();
		} catch (Exception e) {
			theFields = null;
		}
		return theFields;
	}

	@Override
	@Transactional
	public void save(Field theField) {
		entityManager.merge(theField);
	}
}
