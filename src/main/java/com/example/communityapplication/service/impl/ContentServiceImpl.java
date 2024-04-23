package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.dao.ContentTemplateDao;
import com.example.communityapplication.model.*;
import com.example.communityapplication.service.ContentService;
import com.example.communityapplication.service.ContentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContentServiceImpl implements ContentService {
	private ContentDao contentDao;



	@Autowired
	public ContentServiceImpl(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	@Override
	public Content getById(Long contentId) {
		return contentDao.findByContentId(contentId);
	}

	@Override
	public void saveContentAndFieldValues(ContentForm contentForm, User user) {
		// Save the content and get the generated content ID
		contentDao.findByContentId(1);
	}
}
