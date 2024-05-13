package com.example.communityapplication.model;

import java.util.HashMap;
import java.util.Map;

public class ContentForm {
    // A map to hold dynamic field values
    private Map<Integer, String> fieldValues = new HashMap<>();
    private Map<Integer, String> fieldValuesForGeolocationLongitude = new HashMap<>();
    private Map<Integer, String> fieldValuesForGeolocationLatitude = new HashMap<>();


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

    public Map<Integer, String> getFieldValuesForGeolocationLatitude() {
        return fieldValuesForGeolocationLatitude;
    }

    public void setFieldValuesForGeolocationLatitude(Map<Integer, String> fieldValuesForGeolocationLatitude) {
        this.fieldValuesForGeolocationLatitude = fieldValuesForGeolocationLatitude;
    }

    // Helper method to set a specific field value
    public void setFieldValueForGeolocationLatitude(int fieldId, String value) {
        fieldValuesForGeolocationLatitude.put(fieldId, value);
    }

    // Helper method to get a specific field value
    public String getFieldValueForGeolocationLatitude(int fieldId) {
        return fieldValuesForGeolocationLatitude.get(fieldId);
    }

    public Map<Integer, String> getFieldValuesForGeolocationLongitude() {
        return fieldValuesForGeolocationLongitude;
    }

    public void setFieldValuesForGeolocationLongitude(Map<Integer, String> fieldValuesForGeolocationLongitude) {
        this.fieldValuesForGeolocationLongitude = fieldValuesForGeolocationLongitude;
    }

    // Helper method to set a specific field value
    public void setFieldValueForGeolocationLongitude(int fieldId, String value) {
        fieldValuesForGeolocationLongitude.put(fieldId, value);
    }

    // Helper method to get a specific field value
    public String getFieldValueForGeolocationLongitude(int fieldId) {
        return fieldValuesForGeolocationLongitude.get(fieldId);
    }
}