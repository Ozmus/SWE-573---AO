package com.example.communityapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "communities")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "is_archived")
    private boolean is_archived;

    public Community() {

    }

    public Community(String name, String description, String image_url, boolean is_archived) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.is_archived = is_archived;
    }

    public Community(String name, String description, String image_url) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isIs_archived() {
        return is_archived;
    }

    public void setIs_archived(boolean is_archived) {
        this.is_archived = is_archived;
    }
}