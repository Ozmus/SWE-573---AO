package com.example.communityapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "data_type", nullable = false, length = 50)
    private String dataType;

    @ManyToOne
    @JoinColumn(name = "content_template_id", nullable = false)
    private ContentTemplate contentTemplate;

    public Field() {
    }

    public Field(String name, String dataType, ContentTemplate contentTemplate) {
        this.name = name;
        this.dataType = dataType;
        this.contentTemplate = contentTemplate;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public ContentTemplate getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(ContentTemplate contentTemplate) {
        this.contentTemplate = contentTemplate;
    }
}
