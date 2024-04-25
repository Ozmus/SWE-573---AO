package com.example.communityapplication.model;

import java.util.HashMap;
import java.util.Map;

public class ContentCard {
    // A map to hold dynamic field values
    private Map<Integer, String> fieldValues = new HashMap<>();
    private Map<Integer, String> fieldNames = new HashMap<>();

    private ContentTemplate contentTemplate;
    private String title;
    private User user;

    // Constructors, getters, and setters

    public ContentCard() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Map<Integer, String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(Map<Integer, String> fieldNames ) {
        this.fieldNames = fieldNames;
    }

    // Helper method to set a specific field value
    public void setFieldName(int fieldId, String name) {
        fieldNames.put(fieldId, name);
    }

    // Helper method to get a specific field value
    public String getFieldName(int fieldId) {
        return fieldNames.get(fieldId);
    }
}