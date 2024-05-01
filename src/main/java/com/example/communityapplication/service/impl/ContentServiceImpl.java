package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.model.*;
import com.example.communityapplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ContentServiceImpl implements ContentService {
	private ContentDao contentDao;
	private CommunityService communityService;
	private ContentTemplateService contentTemplateService;
	private FieldValueService fieldValueService;

	@Autowired
	public ContentServiceImpl(ContentDao contentDao,
							  CommunityService communityService,
							  ContentTemplateService contentTemplateService,
							  FieldValueService fieldValueService) {
		this.contentDao = contentDao;
		this.communityService = communityService;
		this.contentTemplateService = contentTemplateService;
		this.fieldValueService = fieldValueService;
	}

	@Override
	public Content getById(int contentId) {
		return contentDao.findByContentId(contentId);
	}

	@Override
	public Content saveContent(ContentForm contentForm, User user) {
		return contentDao.save(new Content(contentForm.getTitle(), user, contentForm.getContentTemplate()));
	}

	@Override
	public List<ContentCard> getContentCardsByUser(User user) {
		Stack<ContentTemplate> contentTemplates = new Stack<>();
		for(Community community : communityService.getAllCommunitiesByUser(user)){
			for(ContentTemplate contentTemplate: contentTemplateService.getByCommunity(community)){
				contentTemplates.push(contentTemplate);
			}
		}

		Stack<Content> contents = new Stack<>();
		for(ContentTemplate contentTemplate : contentTemplates){
			for(Content content : contentDao.findByContentTemplateId(contentTemplate)){
				contents.push(content);
			}
		}

		List<ContentCard> contentCards = new ArrayList<>();
		for(Content content : contents){
			ContentCard contentCard = new ContentCard();
			contentCard.setUser(content.getUser());
			contentCard.setTitle(content.getTitle());
			contentCard.setContentTemplate(content.getContentTemplate());
			contentCard.setCommunity(content.getContentTemplate().getCommunity());
			Map<Integer, String> fieldValues = new HashMap<>();
			Map<Integer, String> fieldNames = new HashMap<>();
			for(FieldValue fieldValue : fieldValueService.getFieldValuesByContent(content)){
				fieldValues.put(fieldValue.getField().getId(), fieldValue.getValue());
				fieldNames.put(fieldValue.getField().getId(), fieldValue.getField().getName());
			}
			contentCard.setFieldValues(fieldValues);
			contentCard.setFieldNames(fieldNames);
			contentCards.add(contentCard);
		}
		return contentCards;
	}
}
