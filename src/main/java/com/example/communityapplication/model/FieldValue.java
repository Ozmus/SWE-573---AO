package com.example.communityapplication.model;

import com.example.communityapplication.model.embedded.keys.FieldValueId;
import jakarta.persistence.*;

@Entity
@Table(name = "field_values")
public class FieldValue {

    @EmbeddedId
    private FieldValueId id;

    @Column(name = "value", nullable = false)
    private int value;

    @ManyToOne
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    private Content content;

    @ManyToOne
    @JoinColumn(name = "field_id", insertable = false, updatable = false)
    private Field field;

    // Getters and setters

    public FieldValueId getId() {
        return id;
    }

    public void setId(FieldValueId id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
