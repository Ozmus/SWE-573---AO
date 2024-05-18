package com.example.communityapplication.service.impl;

import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.dao.ContentTemplateDao;
import com.example.communityapplication.dao.FieldValueDao;
import com.example.communityapplication.dao.UserDao;
import com.example.communityapplication.model.*;
import com.example.communityapplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ContentServiceImpl implements ContentService {
	private ContentDao contentDao;
	private ContentTemplateService contentTemplateService;
	private CommunityService communityService;
	private FieldValueService fieldValueService;
	private FieldValueDao fieldValueDao;
	private UserDao userDao;

	@Autowired
	public ContentServiceImpl(ContentDao contentDao,
							  CommunityService communityService,
							  ContentTemplateService
										  contentTemplateService,
							  FieldValueService fieldValueService,
							  FieldValueDao fieldValueDao,
							  UserDao userDao) {
		this.contentDao = contentDao;
		this.communityService = communityService;
		this.contentTemplateService = contentTemplateService;
		this.fieldValueService = fieldValueService;
		this.fieldValueDao = fieldValueDao;
		this.userDao = userDao;
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
	public List<ContentCard> searchByTitle(String title) {
		List<ContentCard> contentCards = setContentCardsByContents(contentDao.searchByTitle(title));
		return contentCards;
	}

	@Override
	public List<ContentCard> searchByFieldValues(String keyword) {
		return setContentCardsByContents(fieldValueDao.search(keyword));
	}

	@Override
	public List<ContentCard> search(String keyword) {
		List<Content> contents = fieldValueDao.search(keyword);
		contents.addAll(contentDao.searchByTitle(keyword));
		List<ContentCard> contentCards = setContentCardsByContents(contents);
		return contentCards;
	}

	@Override
	public List<ContentCard> advancedSearch(AdvancedSearchFields advancedSearchFields) {
		Stack<Content> contents = new Stack<>();
		Community community = null;
		User user = null;
		ContentTemplate contentTemplate = null;
		if(advancedSearchFields.getCommunityName() != null && !advancedSearchFields.getCommunityName().equals("")){
			community = communityService.getByCommunityName(advancedSearchFields.getCommunityName());
			if(advancedSearchFields.getContentTemplateName() != null && !advancedSearchFields.getContentTemplateName().equals("")){
				for(ContentTemplate ct : contentTemplateService.getByCommunity(community)){
					if(ct.getName().equals(advancedSearchFields.getContentTemplateName())){
						contentTemplate = ct;
						break;
					}
				}
			}
		}
		if(advancedSearchFields.getUserName() != null && !advancedSearchFields.getUserName().equals("")){
			user = userDao.findByUserName(advancedSearchFields.getUserName());
		}
		if(community != null
			&& user == null
			&& contentTemplate == null){
			Stack<ContentTemplate> contentTemplates = new Stack<>();
			for(ContentTemplate ct: contentTemplateService.getByCommunity(community)){
				contentTemplates.push(ct);
			}

			for(ContentTemplate ct : contentTemplates){
				for(Content content : contentDao.findByContentTemplateId(ct)){
					contents.push(content);
				}
			}
		}
		else if(community != null
				&& user == null
				&& contentTemplate != null){
			if(contentTemplate.getCommunity().equals(community)){
				for(Content content : contentDao.findByContentTemplateId(contentTemplate)){
					contents.push(content);
				}
			}
		}
		else if(community != null
				&& user != null
				&& contentTemplate == null){
			for(Content content : contentDao.findByUser(user)){
				if(content.getContentTemplate().getCommunity().equals(community)){
					contents.push(content);
				}
			}
		}
		else if(community != null
				&& user != null
				&& contentTemplate != null){
			if(contentTemplate.getCommunity().equals(community)){
				for(Content content : contentDao.findByContentTemplateId(contentTemplate)){
					if (content.getUser().equals(user)){
						contents.push(content);
					}
				}
			}
		}
		else if(community == null
				&& user == null
				&& contentTemplate != null){
			for(Content content : contentDao.findByContentTemplateId(contentTemplate)){
				contents.push(content);
			}
		}
		else if(community == null
				&& user != null
				&& contentTemplate == null){
			for(Content content : contentDao.findByUser(user)){
				contents.push(content);
			}
		}
		else if(community == null
				&& user != null
				&& contentTemplate != null){
			for(Content content : contentDao.findByUser(user)){
				if(content.getContentTemplate().equals(contentTemplate)){
					contents.push(content);
				}
			}
		}

		List<ContentCard> contentCards = setContentCardsByContents(contents);

		return contentCards;
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

		List<ContentCard> contentCards = setContentCardsByContents(contents);
		return contentCards;
	}

	@Override
	public List<ContentCard> getContentCardsPostedByUser(User user) {
		List<Content> contents = contentDao.findByUser(user);
		List<ContentCard> contentCards = setContentCardsByContents(contents);
		return contentCards;
	}

	private List<ContentCard> setContentCardsByContents(List<Content> contents){
		Map<Integer, Content> contentMap = new HashMap<>();
		List<ContentCard> contentCards = new ArrayList<>();
		for(Content content : contents){
			if(!contentMap.containsKey(content.getId())) {
				contentMap.put(content.getId(), content);
				ContentCard contentCard = new ContentCard();
				contentCard.setUser(content.getUser());
				contentCard.setTitle(content.getTitle());
				contentCard.setContentTemplate(content.getContentTemplate());
				contentCard.setCommunity(content.getContentTemplate().getCommunity());
				contentCard.setContent(content);
				Map<Integer, String> fieldValues = new HashMap<>();
				Map<Integer, Field> fields = new HashMap<>();
				for (FieldValue fieldValue : fieldValueService.getFieldValuesByContent(content)) {
					fieldValues.put(fieldValue.getField().getId(), fieldValue.getValue());
					fields.put(fieldValue.getField().getId(), fieldValue.getField());
				}
				contentCard.setFieldValues(fieldValues);
				contentCard.setFields(fields);
				contentCards.add(contentCard);
			}
		}
		return contentCards;
	}
}
