package com.example.communityapplication.service;

import com.example.communityapplication.dao.ContentTemplateDao;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.ContentTemplate;
import com.example.communityapplication.service.impl.ContentTemplateServiceImpl;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContentTemplateServiceImplTest {

    @Mock
    private ContentTemplateDao contentTemplateDao;

    @InjectMocks
    private ContentTemplateServiceImpl contentTemplateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByCommunity() {
        // Given
        Community community = new Community();
        community.setId(1);

        ContentTemplate template1 = new ContentTemplate();
        ContentTemplate template2 = new ContentTemplate();

        List<ContentTemplate> expectedTemplates = List.of(template1, template2);

        when(contentTemplateDao.findByCommunityId(community)).thenReturn(expectedTemplates);

        // When
        List<ContentTemplate> actualTemplates = contentTemplateService.getByCommunity(community);

        // Then
        assertEquals(expectedTemplates, actualTemplates);
        verify(contentTemplateDao).findByCommunityId(community);
    }

    @Test
    void testGetByCommunityNoTemplates() {
        // Given
        Community community = new Community();
        community.setId(1);

        when(contentTemplateDao.findByCommunityId(community)).thenReturn(List.of());

        // When
        List<ContentTemplate> actualTemplates = contentTemplateService.getByCommunity(community);

        // Then
        assertEquals(0, actualTemplates.size());
        verify(contentTemplateDao).findByCommunityId(community);
    }

    @Test
    void testGetById() {
        // Given
        int id = 1;
        ContentTemplate expectedTemplate = new ContentTemplate();
        expectedTemplate.setId(id);

        when(contentTemplateDao.findById(id)).thenReturn(expectedTemplate);

        // When
        ContentTemplate actualTemplate = contentTemplateService.getById(id);

        // Then
        assertEquals(expectedTemplate, actualTemplate);
        verify(contentTemplateDao).findById(id);
    }

    @Test
    void testGetByIdNotFound() {
        // Given
        int id = 1;
        when(contentTemplateDao.findById(id)).thenReturn(null);

        // When
        ContentTemplate actualTemplate = contentTemplateService.getById(id);

        // Then
        assertNull(actualTemplate);
        verify(contentTemplateDao).findById(id);
    }
}
