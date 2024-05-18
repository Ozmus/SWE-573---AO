package com.example.communityapplication.model;

public class AdvancedSearchFields {
    private String communityName;
    private String contentTemplateName;
    private String userName;

    public AdvancedSearchFields() {

    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getContentTemplateName() {
        return contentTemplateName;
    }

    public void setContentTemplateName(String contentTemplateName) {
        this.contentTemplateName = contentTemplateName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}