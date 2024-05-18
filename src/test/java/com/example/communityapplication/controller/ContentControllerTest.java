package com.example.communityapplication.controller;

import com.example.communityapplication.enums.DataType;
import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ContentControllerTest {

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private CommunityService communityService;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private ContentTemplateService contentTemplateService;

    @Mock
    private ContentService contentService;

    @Mock
    private FieldService fieldService;

    @Mock
    private FieldValueService fieldValueService;

    @InjectMocks
    private ContentController contentController;
    private User testUser;
    private Community testCommunity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1);
        testUser.setUserName("testUser");
        testUser.setPassword("password");

        testCommunity = new Community();
        testCommunity.setId(1);
        testCommunity.setName("Test Community");
    }

    @Test
    void testShowSelectCommunity() {
        // Prepare mocks
        when(session.getAttribute("user")).thenReturn(testUser);
        List<UserRole> userRoles = Arrays.asList(new UserRole(new UserRolesId(1, 1), Role.MEMBER));
        when(userRoleService.getRoleByUser(testUser)).thenReturn(userRoles);
        when(communityService.getByCommunityId(anyLong())).thenReturn(testCommunity);

        // Call method
        String result = contentController.showSelectCommunity(session, model);

        // Verify the results
        assertEquals("content/select-community", result);
        verify(model, times(1)).addAttribute(eq("communities"), anyList());
    }

    @Test
    void testShowContentTemplate() {
        // Prepare mocks
        when(communityService.getByCommunityName(anyString())).thenReturn(testCommunity);
        List<ContentTemplate> contentTemplates = Arrays.asList(new ContentTemplate());
        when(contentTemplateService.getByCommunity(testCommunity)).thenReturn(contentTemplates);

        // Call method
        String result = contentController.showContentTemplate("testCommunity", model, session);

        // Verify the results
        assertEquals("content/select-content-template", result);
        verify(model, times(1)).addAttribute("community", testCommunity);
        verify(model, times(1)).addAttribute("contentTemplates", contentTemplates);
    }

    @Test
    void testShowCreateForm() {
        // Prepare mocks
        ContentTemplate contentTemplate = new ContentTemplate();
        contentTemplate.setCommunity(testCommunity);
        contentTemplate.setName("AnyCT");
        contentTemplate.setId(1);

        // Set up mocks for `getById` and `getFieldsByContentTemplateId`
        when(contentTemplateService.getById(anyInt())).thenReturn(contentTemplate);
        List<Field> fields = Arrays.asList(new Field("anyFieldName", DataType.TEXT, contentTemplate));
        when(fieldService.getFieldsByContentTemplateId(anyInt())).thenReturn(fields);

        // Call the method under test
        String result = contentController.showCreateForm("1", model, session);

        // Verify the method result
        assertEquals("content/create-content-form", result);

        // Verify the interactions
        verify(model, times(1)).addAttribute(eq("contentTemplate"), eq(contentTemplate));
        verify(model, times(1)).addAttribute(eq("fields"), eq(fields));
        verify(model, times(1)).addAttribute(eq("contentForm"), any(ContentForm.class));
    }

    @Test
    void testCreateContentSubmit() {
        // Prepare mocks
        ContentForm contentForm = new ContentForm();
        when(session.getAttribute("user")).thenReturn(testUser);
        ContentTemplate contentTemplate = new ContentTemplate();
        when(contentTemplateService.getById(anyInt())).thenReturn(contentTemplate);
        Content content = new Content();
        when(contentService.saveContent(any(ContentForm.class), eq(testUser))).thenReturn(content);

        // Call method
        String result = contentController.createContentSubmit("1", contentForm, session);

        // Verify the results
        assertEquals("home", result);
        verify(contentTemplateService, times(1)).getById(anyInt());
        verify(contentService, times(1)).saveContent(contentForm, testUser);
        verify(fieldValueService, times(1)).saveFieldValues(contentForm, content);
    }
}