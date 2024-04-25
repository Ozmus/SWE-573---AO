package com.example.communityapplication.model;

import java.util.HashMap;
import java.util.Map;

public class ContentForm {
    // A map to hold dynamic field values
    private Map<Integer, String> fieldValues = new HashMap<>();
    private ContentTemplate contentTemplate;
    private String title;

    // Constructors, getters, and setters

    public ContentForm() {}

    public ContentForm(ContentTemplate contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContentTemplate getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(ContentTemplate contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public Map<Integer, String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<Integer, String> fieldValues ) {
        this.fieldValues = fieldValues;
    }

    // Helper method to set a specific field value
    public void setFieldValue(int fieldId, String value) {
        fieldValues.put(fieldId, value);
    }

    // Helper method to get a specific field value
    public String getFieldValue(int fieldId) {
        return fieldValues.get(fieldId);
    }
}