package com.example.communityapplication.service;

import com.example.communityapplication.dao.ContentDao;
import com.example.communityapplication.model.*;
import com.example.communityapplication.service.impl.ContentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContentServiceImplTest {

    @Mock
    private ContentDao contentDao;

    @InjectMocks
    private ContentServiceImpl contentService;

    private ContentTemplate testContentTemplate;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testContentTemplate = new ContentTemplate("ctName", new Community("Community 1", "Description 1", false));
    }

    @Test
    void testGetById() {
        // Given
        int contentId = 1;
        Content expectedContent = new Content();
        expectedContent.setId(contentId);
        when(contentDao.findByContentId(contentId)).thenReturn(expectedContent);

        // When
        Content actualContent = contentService.getById(contentId);

        // Then
        assertEquals(expectedContent, actualContent);
        verify(contentDao).findByContentId(contentId);
    }

    @Test
    void testSaveContent() {
        // Given
        ContentForm contentForm = new ContentForm();
        contentForm.setTitle("Test Title");
        contentForm.setContentTemplate(testContentTemplate);

        User user = new User();
        user.setUserName("testUser");

        Content expectedContent = new Content("Test Title", user, testContentTemplate);
        when(contentDao.save(any(Content.class))).thenReturn(expectedContent);

        // When
        Content actualContent = contentService.saveContent(contentForm, user);

        // Then
        assertEquals(expectedContent, actualContent);
        verify(contentDao).save(any(Content.class));
    }

    @Test
    void testGetByIdContentNotFound() {
        // Given
        int contentId = 1;
        when(contentDao.findByContentId(contentId)).thenReturn(null);

        // When
        Content actualContent = contentService.getById(contentId);

        // Then
        assertNull(actualContent);
        verify(contentDao).findByContentId(contentId);
    }

    @Test
    void testSaveContentDaoException() {
        // Given
        ContentForm contentForm = new ContentForm();
        contentForm.setTitle("Test Title");
        contentForm.setContentTemplate(testContentTemplate);

        User user = new User();
        user.setUserName("testUser");

        when(contentDao.save(any(Content.class))).thenThrow(new RuntimeException("Database error"));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            contentService.saveContent(contentForm, user);
        });

        assertEquals("Database error", exception.getMessage());
        verify(contentDao).save(any(Content.class));
    }
}
