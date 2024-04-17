package com.example.communityapplication.model.embedded.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserRolesId implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "community_id")
    private long communityId;

    // Constructors, getters, and setters
    public UserRolesId() {}

    public UserRolesId(long userId, long communityId) {
        this.userId = userId;
        this.communityId = communityId;
    }

    // Getters and setters
    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }
}

