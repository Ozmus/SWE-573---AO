package com.example.communityapplication.dao;

import com.example.communityapplication.model.Image;

import java.util.List;

public interface ImageDao {
    Image findById(int id);
    Image save(Image imageEntity);
    List<Image> findAll();
}
