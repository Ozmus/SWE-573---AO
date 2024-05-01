package com.example.communityapplication.service;

import com.example.communityapplication.dao.FieldValueDao;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentForm;
import com.example.communityapplication.model.FieldValue;
import com.example.communityapplication.model.embedded.keys.FieldValueId;
import com.example.communityapplication.service.impl.FieldValueServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldValueServiceImplTest {

    @Mock
    private FieldValueDao fieldValueDao;

    @InjectMocks
    private FieldValueServiceImpl fieldValueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFieldValuesByContent() {
        // Given
        Content content = new Content();
        content.setId(1);

        FieldValue fieldValue1 = new FieldValue();
        FieldValue fieldValue2 = new FieldValue();
        List<FieldValue> expectedFieldValues = List.of(fieldValue1, fieldValue2);

        when(fieldValueDao.findByContentId(content)).thenReturn(expectedFieldValues);

        // When
        List<FieldValue> actualFieldValues = fieldValueService.getFieldValuesByContent(content);

        // Then
        assertEquals(expectedFieldValues, actualFieldValues);
        verify(fieldValueDao).findByContentId(content);
    }

    @Test
    void testGetFieldValuesByContentWithNullContent() {
        // Given
        Content content = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            fieldValueService.getFieldValuesByContent(content);
        });

        // Verify that no interaction with fieldValueDao occurs
        verifyNoInteractions(fieldValueDao);
    }

    @Test
    void testSave() {
        // Given
        FieldValue fieldValue = new FieldValue();

        // When
        fieldValueService.save(fieldValue);

        // Then
        verify(fieldValueDao).save(fieldValue);
    }

    @Test
    void testSaveWithNullFieldValue() {
        // Given
        FieldValue fieldValue = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            fieldValueService.save(fieldValue);
        });

        // Verify that no interaction with fieldValueDao occurs
        verifyNoInteractions(fieldValueDao);
    }

    @Test
    void testSaveFieldValues() {
        // Given
        ContentForm contentForm = new ContentForm();
        contentForm.setFieldValues(new HashMap<>());
        contentForm.getFieldValues().put(1, "value1");
        contentForm.getFieldValues().put(2, "value2");

        Content content = new Content();
        content.setId(1);

        // When
        fieldValueService.saveFieldValues(contentForm, content);

        // Then
        verify(fieldValueDao, times(2)).save(any(FieldValue.class));
    }

    @Test
    void testSaveFieldValuesWithNullContentForm() {
        // Given
        ContentForm contentForm = null;
        Content content = new Content();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            fieldValueService.saveFieldValues(contentForm, content);
        });

        // Verify that no interaction with fieldValueDao occurs
        verifyNoInteractions(fieldValueDao);
    }

    @Test
    void testSaveFieldValuesWithNullContent() {
        // Given
        ContentForm contentForm = new ContentForm();
        contentForm.setFieldValues(new HashMap<>());

        // Content is null
        Content content = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            fieldValueService.saveFieldValues(contentForm, content);
        });

        // Verify that no interaction with fieldValueDao occurs
        verifyNoInteractions(fieldValueDao);
    }

    @Test
    void testSaveFieldValuesDatabaseException() {
        // Given
        ContentForm contentForm = new ContentForm();
        contentForm.setFieldValues(new HashMap<>());
        contentForm.getFieldValues().put(1, "value1");
        contentForm.getFieldValues().put(2, "value2");

        Content content = new Content();
        content.setId(1);

        // Mocking a database exception from fieldValueDao.save
        doThrow(new RuntimeException("Database error")).when(fieldValueDao).save(any(FieldValue.class));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fieldValueService.saveFieldValues(contentForm, content);
        });

        // Verify the exception message
        assertEquals("Database error", exception.getMessage());

        // Verify that fieldValueDao.save was called twice
        verify(fieldValueDao, times(1)).save(any(FieldValue.class));
    }
}
