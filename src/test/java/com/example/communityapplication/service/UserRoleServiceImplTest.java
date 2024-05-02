package com.example.communityapplication.service;

import com.example.communityapplication.dao.UserRoleDao;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.impl.UserRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleServiceImplTest {

    @Mock
    private UserRoleDao userRoleDao;

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoleByUserAndCommunityId() {
        // Given
        UserRolesId userRolesId = new UserRolesId(1, 1);
        UserRole expectedUserRole = new UserRole();

        when(userRoleDao.findByUserAndCommunityId(userRolesId)).thenReturn(expectedUserRole);

        // When
        UserRole actualUserRole = userRoleService.getRoleByUserAndCommunityId(userRolesId);

        // Then
        assertEquals(expectedUserRole, actualUserRole);
        verify(userRoleDao).findByUserAndCommunityId(userRolesId);
    }

    @Test
    void testGetRoleByUser() {
        // Given
        User user = new User();
        user.setId(1);
        List<UserRole> expectedUserRoles = List.of(new UserRole(), new UserRole());

        when(userRoleDao.findByUserId(user.getId())).thenReturn(expectedUserRoles);

        // When
        List<UserRole> actualUserRoles = userRoleService.getRoleByUser(user);

        // Then
        assertEquals(expectedUserRoles, actualUserRoles);
        verify(userRoleDao).findByUserId(user.getId());
    }

    @Test
    void testSave() {
        // Given
        UserRole userRole = new UserRole();

        // When
        userRoleService.save(userRole);

        // Then
        verify(userRoleDao).save(userRole);
    }

    @Test
    void testGetRoleByUserAndCommunityIdWithNullInput() {
        // Given
        UserRolesId userRolesId = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoleService.getRoleByUserAndCommunityId(userRolesId);
        });

        // Verify that no interaction with userRoleDao occurs
        verifyNoInteractions(userRoleDao);
    }

    @Test
    void testGetRoleByUserWithNullUser() {
        // Given
        User user = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoleService.getRoleByUser(user);
        });

        // Verify that no interaction with userRoleDao occurs
        verifyNoInteractions(userRoleDao);
    }

    @Test
    void testSaveWithNullUserRole() {
        // Given
        UserRole userRole = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoleService.save(userRole);
        });

        // Verify that no interaction with userRoleDao occurs
        verifyNoInteractions(userRoleDao);
    }

    @Test
    void testSaveDatabaseException() {
        // Given
        UserRole userRole = new UserRole();

        // Mocking a database exception from userRoleDao.save
        doThrow(new RuntimeException("Database error")).when(userRoleDao).save(userRole);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userRoleService.save(userRole);
        });

        // Verify the exception message
        assertEquals("Database error", exception.getMessage());

        // Verify that userRoleDao.save was called
        verify(userRoleDao).save(userRole);
    }
}
