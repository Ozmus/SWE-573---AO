package com.example.communityapplication.service;

import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.model.ContentTemplate;
import com.example.communityapplication.model.Field;
import com.example.communityapplication.service.impl.FieldServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    @Mock
    private FieldDao fieldDao;

    @Mock
    private ContentTemplateService contentTemplateService;

    @InjectMocks
    private FieldServiceImpl fieldService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFieldsByContentTemplateId() {
        // Given
        int contentTemplateId = 1;
        ContentTemplate contentTemplate = new ContentTemplate();
        contentTemplate.setId(contentTemplateId);

        Field field1 = new Field();
        Field field2 = new Field();
        List<Field> expectedFields = List.of(field1, field2);

        // Mocking the behavior of ContentTemplateService and FieldDao
        when(contentTemplateService.getById(contentTemplateId)).thenReturn(contentTemplate);
        when(fieldDao.findByContentTemplateId(contentTemplate)).thenReturn(expectedFields);

        // When
        List<Field> actualFields = fieldService.getFieldsByContentTemplateId(contentTemplateId);

        // Then
        assertEquals(expectedFields, actualFields);
        verify(contentTemplateService).getById(contentTemplateId);
        verify(fieldDao).findByContentTemplateId(contentTemplate);
    }

    @Test
    void testGetFieldsByContentTemplateIdContentTemplateNotFound() {
        // Given
        int contentTemplateId = 1;

        // Mocking the behavior of ContentTemplateService
        when(contentTemplateService.getById(contentTemplateId)).thenReturn(null);

        // When
        List<Field> actualFields = fieldService.getFieldsByContentTemplateId(contentTemplateId);

        // Then
        assertEquals(0, actualFields.size());
        verify(contentTemplateService).getById(contentTemplateId);
        verifyNoInteractions(fieldDao);
    }
}
