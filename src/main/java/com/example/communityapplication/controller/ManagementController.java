package com.example.communityapplication.controller;

import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/management")
public class ManagementController {
    private CommunityService communityService;
    private UserRoleService userRoleService;
    private UserService userService;
    private ContentTemplateService contentTemplateService;


    @Autowired
    public ManagementController(CommunityService communityService,
                                UserRoleService userRoleService,
                                ContentTemplateService contentTemplateService,
                                UserService userService) {
        this.communityService = communityService;
        this.userRoleService = userRoleService;
        this.contentTemplateService = contentTemplateService;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/selectCommunity")
    public String showSelectCommunity(HttpSession session,
                               Model theModel) {
        List<Community> communities = communityService.getCommunitiesForManagement((User)session.getAttribute("user"));
        theModel.addAttribute("communities", communities);
        return "management/select-community";
    }

    @GetMapping("/selectOperation")
    public String showSelectOperations(@RequestParam("name") String name, Model theModel, HttpSession session) {
        Community community = communityService.getByCommunityName(name);
        theModel.addAttribute("community", community);
        return "management/select-operations";
    }

    @GetMapping("/selectContentTemplate")
    public String showContentTemplate(@RequestParam("name") String name, Model theModel, HttpSession session) {
        Community community = communityService.getByCommunityName(name);
        List<ContentTemplate> contentTemplates = contentTemplateService.getByCommunity(community);
        theModel.addAttribute("community", community);
        theModel.addAttribute("contentTemplates", contentTemplates);
        return "management/select-content-template";
    }

    @GetMapping("/selectUser")
    public String showUsers(@RequestParam("name") String name, Model theModel, HttpSession session) {
        Community community = communityService.getByCommunityName(name);
        User user = (User)session.getAttribute("user");
        List<User> users = userService.getUsersForManagement(community.getId(), user);

        theModel.addAttribute("community", community);
        theModel.addAttribute("users", users);
        return "management/select-users";
    }

    @GetMapping("/userDetails")
    public String showUserDetails(@RequestParam("communityName") String communityName,
                                  @RequestParam("userName") String userName,
                                  Model theModel,
                                  HttpSession session) {
        Community community = communityService.getByCommunityName(communityName);
        User user = userService.getByUserName(userName);
        UserRole userRole = userRoleService.getRoleByUserAndCommunityId(new UserRolesId(user.getId(), community.getId()));
        theModel.addAttribute("community", community);
        theModel.addAttribute("user", user);
        theModel.addAttribute("userRole", userRole);
        return "management/user-details";
    }
    @PostMapping("/userDetails/kick")
    public String kickUser(@RequestParam("communityName") String communityName,
                                @RequestParam("userName") String userName,
                                Model theModel,
                                HttpSession session) {
        Community community = communityService.getByCommunityName(communityName);
        User userToDelete = userService.getByUserName(userName);
        Role currentUserRole = userRoleService.getRoleByUserAndCommunityId(new UserRolesId(((User)session.getAttribute("user")).getId(), community.getId())).getRole();
        userRoleService.kickUser(currentUserRole, userToDelete, community);

        return this.showUsers(communityName, theModel, session);
    }

    @PostMapping("/userDetails/promote")
    public String promoteUser(@RequestParam("communityName") String communityName,
                           @RequestParam("userName") String userName,
                           Model theModel,
                           HttpSession session) {
        Community community = communityService.getByCommunityName(communityName);
        User userToPromote = userService.getByUserName(userName);
        User currentUser = (User)session.getAttribute("user");
        Role currentUserRole = userRoleService.getRoleByUserAndCommunityId(new UserRolesId(currentUser.getId(), community.getId())).getRole();
        userRoleService.promoteUser(currentUserRole, userToPromote, community);

        return this.showUsers(communityName, theModel, session);
    }
}