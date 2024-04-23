package com.example.communityapplication.model;

import java.util.HashMap;
import java.util.Map;

public class ContentForm {
    // A map to hold dynamic field values
    private Map<Long, String> fieldValues = new HashMap<>();
    private String title;

    // Constructors, getters, and setters

    public ContentForm() {}

    public ContentForm(Map<Long, String> fieldValues, String title) {
        this.fieldValues = fieldValues;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<Long, String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<Long, String> fieldValues ) {
        this.fieldValues = fieldValues;
    }

    // Helper method to set a specific field value
    public void setFieldValue(long fieldId, String value) {
        fieldValues.put(fieldId, value);
    }

    // Helper method to get a specific field value
    public String getFieldValue(long fieldId) {
        return fieldValues.get(fieldId);
    }
}