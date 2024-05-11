package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentTemplate;
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
	public Field findByFieldId(int id) {
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
	public List<Field> findByContentTemplateId(ContentTemplate contentTemplate) {
		TypedQuery<Field> theQuery = entityManager.createQuery("from Field  where contentTemplate =:fContentTemplate", Field.class);
		theQuery.setParameter("fContentTemplate", contentTemplate);

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
	public Field save(Field theField) {
		return entityManager.merge(theField);
	}

	@Override
	@Transactional
	public void delete(Field theField) {
		entityManager.remove(theField);
	}
}
