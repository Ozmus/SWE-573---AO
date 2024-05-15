package com.example.communityapplication.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

public class WebCommunity {
    private String name;
    private String description;
    private MultipartFile image;
    private boolean is_archived;

    public WebCommunity() {

    }

    public WebCommunity(String name, String description, boolean is_archived) {
        this.name = name;
        this.description = description;
        this.is_archived = is_archived;
    }

    public WebCommunity(String name, String description) {
        this.name = name;
        this.description = description;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public boolean isIs_archived() {
        return is_archived;
    }

    public void setIs_archived(boolean is_archived) {
        this.is_archived = is_archived;
    }
}