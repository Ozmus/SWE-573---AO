package com.example.communityapplication.dao.impl;

import com.example.communityapplication.dao.ImageDao;
import com.example.communityapplication.model.Image;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class ImageDaoImpl implements ImageDao {

    private final EntityManager entityManager;

    @Autowired
    public ImageDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Image findById(int id) {
        return entityManager.find(Image.class, id);
    }

    @Override
    @Transactional
    public Image save(Image imageEntity) {
        return entityManager.merge(imageEntity);
    }

    @Override
    public List<Image> findAll() {
        TypedQuery<Image> query = entityManager.createQuery("SELECT i FROM Image i", Image.class);
        return query.getResultList();
    }
}

