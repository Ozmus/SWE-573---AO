package com.example.communityapplication.model;

import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import jakarta.persistence.*;


@Entity
@Table(name = "user_roles")
public class UserRole {

    @EmbeddedId
    private UserRolesId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    // Constructor, getters, and setters
    public UserRole() {}

    public UserRole(UserRolesId id, Role role) {
        this.id = id;
        this.role = role;
    }

    // Getters and setters
    public UserRolesId getId() {
        return id;
    }

    public void setId(UserRolesId id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
