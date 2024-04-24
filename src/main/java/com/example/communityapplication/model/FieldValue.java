package com.example.communityapplication.model;

import com.example.communityapplication.model.embedded.keys.FieldValueId;
import jakarta.persistence.*;

@Entity
@Table(name = "field_values")
public class FieldValue {

    @EmbeddedId
    private FieldValueId id;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    private Content content;

    @ManyToOne
    @JoinColumn(name = "field_id", insertable = false, updatable = false)
    private Field field;

    public FieldValue() {

    }

    public FieldValue(FieldValueId fieldValueId, String fieldValue) {
        this.id =fieldValueId;
        this.value = fieldValue;
    }

    // Getters and setters

    public FieldValueId getId() {
        return id;
    }

    public void setId(FieldValueId id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
