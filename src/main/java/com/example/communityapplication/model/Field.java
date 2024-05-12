package com.example.communityapplication.model;

import com.example.communityapplication.enums.DataType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private DataType dataType;

    @ManyToOne
    @JoinColumn(name = "content_template_id", nullable = false)
    private ContentTemplate contentTemplate;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    List<FieldValue> fieldValues;

    public Field() {
    }

    public Field(String name, DataType dataType, ContentTemplate contentTemplate) {
        this.name = name;
        this.dataType = dataType;
        this.contentTemplate = contentTemplate;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public ContentTemplate getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(ContentTemplate contentTemplate) {
        this.contentTemplate = contentTemplate;
    }
}
