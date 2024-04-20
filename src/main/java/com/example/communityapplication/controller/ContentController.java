package com.example.communityapplication.controller;

import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.service.CommunityService;
import com.example.communityapplication.service.UserRoleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {
    private CommunityService communityService;
    private UserRoleService userRoleService;


    @Autowired
    public ContentController(CommunityService communityService, UserRoleService userRoleService) {
        this.communityService = communityService;
        this.userRoleService = userRoleService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @GetMapping("/postForm")
    public String showPostForm() {

        return "post-form";
    }

    @GetMapping("/selectCommunity")
    public String showSelectCommunity(HttpSession session,
                               Model theModel) {
        List<UserRole> userRoles = userRoleService.getRoleByUser((User)session.getAttribute("user"));
        List<Community> communities = new ArrayList<>();
        for(UserRole userRole : userRoles){
            communities.add(communityService.getByCommunityId(userRole.getId().getCommunityId()));
        }
        theModel.addAttribute("communities", communities);
        return "/content/content-select-community";
    }
}









