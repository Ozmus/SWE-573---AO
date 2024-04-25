package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.model.*;
import com.example.communityapplication.service.ContentService;
import com.example.communityapplication.service.FieldValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContentServiceImpl implements ContentService {
	private ContentDao contentDao;

	@Autowired
	public ContentServiceImpl(ContentDao contentDao, FieldValueService fieldValueService) {
		this.contentDao = contentDao;
	}

	@Override
	public Content getById(Long contentId) {
		return contentDao.findByContentId(contentId);
	}

	@Override
	public Content saveContent(ContentForm contentForm, User user) {
		return contentDao.save(new Content(contentForm.getTitle(), user, contentForm.getContentTemplate()));
	}
}
