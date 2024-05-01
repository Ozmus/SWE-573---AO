package com.example.communityapplication.service;

import com.example.communityapplication.dao.UserDao;
import com.example.communityapplication.model.User;
import com.example.communityapplication.service.impl.UserServiceImpl;
import com.example.communityapplication.user.WebUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByUserName() {
        // Given
        String userName = "testUser";
        User expectedUser = new User();
        expectedUser.setUserName(userName);

        when(userDao.findByUserName(userName)).thenReturn(expectedUser);

        // When
        User actualUser = userService.getByUserName(userName);

        // Then
        assertEquals(expectedUser, actualUser);
        verify(userDao).findByUserName(userName);
    }

    @Test
    void testSave() {
        // Given
        WebUser webUser = new WebUser();
        webUser.setUserName("testUser");
        webUser.setPassword("password");
        webUser.setFirstName("John");
        webUser.setLastName("Doe");
        webUser.setEmail("john.doe@example.com");

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(webUser.getPassword())).thenReturn(encodedPassword);

        // When
        userService.save(webUser);

        // Then
        verify(passwordEncoder).encode(webUser.getPassword());
        verify(userDao).save(any(User.class));
    }

    @Test
    void testLoadUserByUsername() {
        // Given
        String userName = "testUser";
        User user = new User();
        user.setUserName(userName);
        user.setPassword("password");

        when(userDao.findByUserName(userName)).thenReturn(user);

        // When
        org.springframework.security.core.userdetails.UserDetails userDetails = userService.loadUserByUsername(userName);

        // Then
        assertNotNull(userDetails);
        assertEquals(user.getUserName(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
        verify(userDao).findByUserName(userName);
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        // Given
        String userName = "testUser";

        when(userDao.findByUserName(userName)).thenReturn(null);

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(userName);
        });

        verify(userDao).findByUserName(userName);
    }
}