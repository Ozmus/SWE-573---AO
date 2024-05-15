package com.example.communityapplication.service;

import com.example.communityapplication.dao.CommunityDao;
import com.example.communityapplication.dao.ContentTemplateDao;
import com.example.communityapplication.dao.FieldDao;
import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
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
    private ContentTemplateDao contentTemplateDao;

    @Mock
    private FieldDao fieldDao;
    @Mock
    private UserRoleService userRoleService;

    @InjectMocks
    private CommunityServiceImpl communityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByCommunityId() {
        // Arrange
        int communityId = 1;
        Community expectedCommunity = new Community("Community1", "Description", false);
        expectedCommunity.setId(communityId);
        when(communityDao.findByCommunityId(communityId)).thenReturn(expectedCommunity);

        // Act
        Community actualCommunity = communityService.getByCommunityId(communityId);

        // Assert
        assertEquals(expectedCommunity, actualCommunity);
        verify(communityDao).findByCommunityId(communityId);
    }

    @Test
    void testGetByCommunityName() {
        // Arrange
        String communityName = "Community1";
        Community expectedCommunity = new Community(communityName, "Description",  false);
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
        expectedCommunities.add(new Community("Community1", "Description1",  false));
        expectedCommunities.add(new Community("Community2", "Description2",  false));
        when(communityDao.findAllCommunities()).thenReturn(expectedCommunities);

        // Act
        List<Community> actualCommunities = communityService.getAllCommunities();

        // Assert
        assertEquals(expectedCommunities, actualCommunities);
        verify(communityDao).findAllCommunities();
    }

    @Test
    void testGetAllCommunitiesByUser() {
        // Arrange
        User user = new User();
        user.setId(1);

        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(new UserRole(new UserRolesId(user.getId(), 1), Role.MEMBER));

        Community community1 = new Community("Community1", "Description1", false);
        community1.setId(1);

        List<Community> expectedCommunities = List.of(community1);

        when(userRoleService.getRoleByUser(user)).thenReturn(userRoles);
        when(communityDao.findByCommunityId(userRoles.get(0).getId().getCommunityId())).thenReturn(community1);

        // Act
        List<Community> actualCommunities = communityService.getAllCommunitiesByUser(user);

        // Assert
        assertEquals(expectedCommunities, actualCommunities);
        verify(userRoleService).getRoleByUser(user);
        verify(communityDao).findByCommunityId(userRoles.get(0).getId().getCommunityId());
    }

    @Test
    void testGetCommunitiesForManagement() {
        // Arrange
        User user = new User();
        user.setId(1);

        UserRole modRole = new UserRole(new UserRolesId(user.getId(), 1), Role.MOD);
        UserRole ownerRole = new UserRole(new UserRolesId(user.getId(), 2), Role.OWNER);

        List<UserRole> userRoles = List.of(modRole, ownerRole);

        Community modCommunity = new Community("Community1", "Description1", false);
        modCommunity.setId(1);

        Community ownerCommunity = new Community("Community2", "Description2", false);
        ownerCommunity.setId(2);

        List<Community> expectedCommunities = List.of(modCommunity, ownerCommunity);

        when(userRoleService.getRoleByUser(user)).thenReturn(userRoles);
        when(communityDao.findByCommunityId(modRole.getId().getCommunityId())).thenReturn(modCommunity);
        when(communityDao.findByCommunityId(ownerRole.getId().getCommunityId())).thenReturn(ownerCommunity);

        // Act
        List<Community> actualCommunities = communityService.getCommunitiesForManagement(user);

        // Assert
        assertEquals(expectedCommunities, actualCommunities);
        verify(userRoleService).getRoleByUser(user);
        verify(communityDao).findByCommunityId(modRole.getId().getCommunityId());
        verify(communityDao).findByCommunityId(ownerRole.getId().getCommunityId());
    }

    @Test
    void testCreateCommunity() {
        // Arrange
        User currentUser = mock(User.class);
        Community community = new Community("Community1", "Description", false);
        ContentTemplate contentTemplate = new ContentTemplate("Default", community);

        community.setId(1);
        when(currentUser.getId()).thenReturn(1);
        when(communityDao.findByCommunityName(community.getName())).thenReturn(community);
        when(contentTemplateDao.findByNameAndCommunityId(contentTemplate.getName(), community)).thenReturn(contentTemplate);

        // Act
        communityService.createCommunity(community, currentUser);

        // Assert
        verify(communityDao).save(community);
        verify(userRoleService).save(any(UserRole.class));
        verify(fieldDao).save(any(Field.class));
    }

    @Test
    void testIsExist_ExistingCommunity() {
        // Arrange
        String communityName = "Community1";
        Community existingCommunity = new Community(communityName, "Description", false);
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

    @Test
    void testJoinCommunity() {
        // Arrange
        Community community = new Community();
        community.setId(1);
        community.setName("Test Community");

        User user = new User();
        user.setId(1);

        // Act
        communityService.joinCommunity(community, user);

        // Assert
        verify(userRoleService).save(argThat(userRole ->
                userRole.getId().getUserId() == user.getId() &&
                        userRole.getId().getCommunityId() == community.getId() &&
                        userRole.getRole() == Role.MEMBER
        ));
    }

    @Test
    void testIsMember() {
        // Arrange
        Community community = new Community();
        community.setId(1);

        User user = new User();
        user.setId(1);

        UserRole userRole = new UserRole(new UserRolesId(user.getId(), community.getId()), Role.MEMBER);

        when(userRoleService.getRoleByUserAndCommunityId(any(UserRolesId.class))).thenReturn(userRole);

        // Act
        boolean isMember = communityService.isMember(community, user);

        // Assert
        assertTrue(isMember);
        verify(userRoleService).getRoleByUserAndCommunityId(any(UserRolesId.class));
    }
}
