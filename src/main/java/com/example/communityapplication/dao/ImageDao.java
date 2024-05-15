package com.example.communityapplication.dao;

import com.example.communityapplication.model.Image;

import java.util.List;

public interface ImageDao {
    Image findById(Long id);
    void save(Image imageEntity);
    List<Image> findAll();
    List<Image> search(String keyword);
}
