package com.example.communityapplication.controller;

import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.ContentTemplate;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.CommunityService;
import com.example.communityapplication.service.ContentTemplateService;
import com.example.communityapplication.service.UserRoleService;
import com.example.communityapplication.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ManagementControllerTest {

    @Mock
    private CommunityService communityService;
    @Mock
    private UserRoleService userRoleService;
    @Mock
    private UserService userService;
    @Mock
    private ContentTemplateService contentTemplateService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ManagementController managementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testShowSelectCommunity() {
        Community modCommunity = new Community();
        Community ownerCommunity = new Community();
        List<Community> communities = List.of(modCommunity, ownerCommunity);

        when(communityService.getCommunitiesForManagement(any())).thenReturn(communities);

        // Act
        String viewName = managementController.showSelectCommunity(session, model);

        // Assert
        assertEquals("management/select-community", viewName);
        verify(model).addAttribute("communities", communities);
    }

    @Test
    void testShowSelectOperations() {
        // Arrange
        String communityName = "TestCommunity";
        Community community = new Community();
        when(communityService.getByCommunityName(communityName)).thenReturn(community);

        // Act
        String viewName = managementController.showSelectOperations(communityName, model, session);

        // Assert
        assertEquals("management/select-operations", viewName);
        verify(model).addAttribute("community", community);  // Check community added to the model
    }
    @Test
    void testShowContentTemplate() {
        // Arrange
        String communityName = "TestCommunity";
        Community community = new Community();
        when(communityService.getByCommunityName(communityName)).thenReturn(community);

        List<ContentTemplate> contentTemplates = new ArrayList<>();
        when(contentTemplateService.getByCommunity(community)).thenReturn(contentTemplates);

        // Act
        String viewName = managementController.showContentTemplate(communityName, model, session);

        // Assert
        assertEquals("management/select-content-template", viewName);
        verify(model).addAttribute("community", community);
        verify(model).addAttribute("contentTemplates", contentTemplates);
    }
    @Test
    void testShowUsers() {
        // Arrange
        String communityName = "TestCommunity";
        Community community = new Community();
        when(communityService.getByCommunityName(communityName)).thenReturn(community);

        List<UserRole> userRoles = new ArrayList<>();
        when(userRoleService.getRoleByCommunityId(community.getId())).thenReturn(userRoles);

        List<User> users = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            User user = new User();
            when(userService.getByUserId(userRole.getId().getUserId())).thenReturn(user);
            users.add(user);
        }

        // Act
        String viewName = managementController.showUsers(communityName, model, session);

        // Assert
        assertEquals("management/select-users", viewName);
        verify(model).addAttribute("community", community);
        verify(model).addAttribute("users", users);
    }


}

