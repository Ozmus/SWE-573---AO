package com.example.communityapplication.service;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.service.impl.CommunityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommunityServiceImplTest {

    @Mock
    private CommunityDao communityDao;

    @Mock
    private UserRoleService userRoleService;

    @InjectMocks
    private CommunityServiceImpl communityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByCommunityName() {
        // Arrange
        String communityName = "Community1";
        Community expectedCommunity = new Community(communityName, "Description", "Image URL", false);
        when(communityDao.findByCommunityName(communityName)).thenReturn(expectedCommunity);

        // Act
        Community actualCommunity = communityService.getByCommunityName(communityName);

        // Assert
        assertEquals(expectedCommunity, actualCommunity);
        verify(communityDao).findByCommunityName(communityName);
    }

    @Test
    void testGetAllCommunities() {
        // Arrange
        List<Community> expectedCommunities = new ArrayList<>();
        expectedCommunities.add(new Community("Community1", "Description1", "Image URL1", false));
        expectedCommunities.add(new Community("Community2", "Description2", "Image URL2", false));
        when(communityDao.findAllCommunities()).thenReturn(expectedCommunities);

        // Act
        List<Community> actualCommunities = communityService.getAllCommunities();

        // Assert
        assertEquals(expectedCommunities, actualCommunities);
        verify(communityDao).findAllCommunities();
    }

    @Test
    void testCreateCommunity() {
        // Arrange
        User currentUser = mock(User.class);
        Community community = new Community("Community1", "Description", "Image URL", false);
        community.setId(1L);
        when(currentUser.getId()).thenReturn(1L);
        when(communityDao.findByCommunityName(community.getName())).thenReturn(community);
        // Act
        communityService.createCommunity(community, currentUser);

        // Assert
        verify(communityDao).save(community);
        verify(userRoleService).save(any(UserRole.class));
    }

    @Test
    void testIsExist_ExistingCommunity() {
        // Arrange
        String communityName = "Community1";
        Community existingCommunity = new Community(communityName, "Description", "Image URL", false);
        when(communityDao.findByCommunityName(communityName)).thenReturn(existingCommunity);

        // Act
        boolean result = communityService.isExist(communityName);

        // Assert
        assertTrue(result);
        verify(communityDao).findByCommunityName(communityName);
    }

    @Test
    void testIsExist_NonExistingCommunity() {
        // Arrange
        String communityName = "NonExistingCommunity";
        when(communityDao.findByCommunityName(communityName)).thenReturn(null);

        // Act
        boolean result = communityService.isExist(communityName);

        // Assert
        assertFalse(result);
        verify(communityDao).findByCommunityName(communityName);
    }
}
