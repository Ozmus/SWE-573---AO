package com.example.communityapplication.model;

import java.util.HashMap;
import java.util.Map;

public class ContentCard {
    // A map to hold dynamic field values
    private Map<Integer, String> fieldValues = new HashMap<>();
    private Map<Integer, Field> fieldNames = new HashMap<>();

    private ContentTemplate contentTemplate;
    private String title;
    private User user;
    private Community community;
    private Content content;

    // Constructors, getters, and setters

    public ContentCard() {}

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
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

    public Map<Integer, Field> getFieldNames() {
        return fieldNames;
    }

    public void setFields(Map<Integer, Field> fieldNames ) {
        this.fieldNames = fieldNames;
    }

    // Helper method to set a specific field value
    public void setField(int fieldId, Field field) {
        fieldNames.put(fieldId, field);
    }

    // Helper method to get a specific field value
    public Field getField(int fieldId) {
        return fieldNames.get(fieldId);
    }
}