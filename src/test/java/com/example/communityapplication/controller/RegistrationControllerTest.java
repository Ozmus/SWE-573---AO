package com.example.communityapplication.controller;

import com.example.communityapplication.model.User;
import com.example.communityapplication.user.WebUser;
import com.example.communityapplication.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegistrationControllerTest {

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowMyLoginPage() {
        // Given

        // When
        String viewName = registrationController.showMyLoginPage(model);

        // Then
        // Use matchers for all arguments:
        verify(model).addAttribute(eq("webUser"), any(WebUser.class));
        assertEquals("register/registration-form", viewName);
    }

    @Test
    void testProcessRegistrationFormWithBindingErrors() {
        // Given
        WebUser webUser = new WebUser();
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = registrationController.processRegistrationForm(webUser, bindingResult, session, model);

        // Then
        assertEquals("register/registration-form", viewName);
        verify(model, never()).addAttribute(eq("registrationError"), anyString());
        verify(userService, never()).getByUserName(webUser.getUserName());
        verify(userService, never()).save(any(WebUser.class));
        verify(session, never()).setAttribute("user", webUser);
    }

    @Test
    void testProcessRegistrationFormUserAlreadyExists() {
        // Given
        WebUser webUser = new WebUser();
        webUser.setUserName("existingUser");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.getByUserName(webUser.getUserName())).thenReturn(new User());

        // When
        String viewName = registrationController.processRegistrationForm(webUser, bindingResult, session, model);

        // Then
        assertEquals("register/registration-form", viewName);
        verify(model).addAttribute("registrationError", "User name already exists.");
        verify(userService).getByUserName(webUser.getUserName());
        verify(userService, never()).save(any(WebUser.class));
        verify(session, never()).setAttribute("user", webUser);
    }

    @Test
    void testProcessRegistrationFormSuccess() {
        // Given
        WebUser webUser = new WebUser();
        webUser.setUserName("newUser");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.getByUserName(webUser.getUserName())).thenReturn(null);

        // When
        String viewName = registrationController.processRegistrationForm(webUser, bindingResult, session, model);

        // Then
        assertEquals("register/registration-confirmation", viewName);
        verify(userService).save(webUser);
        verify(session).setAttribute("user", webUser);
    }
}