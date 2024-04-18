package com.example.communityapplication.model.embedded.keys;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FieldValueId implements Serializable {

    @Column(name = "content_id")
    private int contentId;

    @Column(name = "field_id")
    private int fieldId;

    // Default constructor
    public FieldValueId() {}

    // Constructor with parameters
    public FieldValueId(int contentId, int fieldId) {
        this.contentId = contentId;
        this.fieldId = fieldId;
    }

    // Getters and setters

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    // Override equals and hashCode methods for proper comparison

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FieldValueId that = (FieldValueId) obj;
        return contentId == that.contentId && fieldId == that.fieldId;
    }

    @Override
    public int hashCode() {
        int result = contentId;
        result = 31 * result + fieldId;
        return result;
    }
}

