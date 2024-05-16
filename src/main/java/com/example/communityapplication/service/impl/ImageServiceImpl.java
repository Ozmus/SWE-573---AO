package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.FieldValueDao;
import com.example.communityapplication.dao.ImageDao;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentForm;
import com.example.communityapplication.model.FieldValue;
import com.example.communityapplication.model.Image;
import com.example.communityapplication.model.embedded.keys.FieldValueId;
import com.example.communityapplication.service.FieldValueService;
import com.example.communityapplication.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

	private ImageDao imageDao;

	@Autowired
	public ImageServiceImpl(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	@Override
	public Image getById(int id) {
		return imageDao.findById(id);
	}
}
