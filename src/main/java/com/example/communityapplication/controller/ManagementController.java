package com.example.communityapplication.controller;

import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
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
        List<UserRole> userRoles = userRoleService.getRoleByUser((User)session.getAttribute("user"));
        List<Community> communities = new ArrayList<>();
        for(UserRole userRole : userRoles){
            if(userRole.getRole().equals(Role.MOD) || userRole.getRole().equals(Role.OWNER)){
                communities.add(communityService.getByCommunityId(userRole.getId().getCommunityId()));
            }
        }
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
        List<UserRole> userRoles = userRoleService.getRoleByCommunityId(community.getId());
        List<User> users = new ArrayList<>();
        for(UserRole userRole : userRoles){
            users.add(userService.getByUserId(userRole.getId().getUserId()));
        }
        theModel.addAttribute("community", community);
        theModel.addAttribute("users", users);
        return "management/select-users";
    }
}