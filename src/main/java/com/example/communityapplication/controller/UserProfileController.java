package com.example.communityapplication.controller;

import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.ContentCard;
import com.example.communityapplication.model.User;
import com.example.communityapplication.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private ContentService contentService;
    private UserRoleService userRoleService;
    private UserService userService;
    private ContentTemplateService contentTemplateService;
    private FieldService fieldService;

    @Autowired
    public UserProfileController(ContentService contentService,
                                UserRoleService userRoleService,
                                ContentTemplateService contentTemplateService,
                                UserService userService,
                                FieldService fieldService) {
        this.contentService = contentService;
        this.userRoleService = userRoleService;
        this.contentTemplateService = contentTemplateService;
        this.userService = userService;
        this.fieldService = fieldService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String showProfile(HttpSession session,
                           Model theModel) {
        try {
            User user = (User) session.getAttribute("user");
            List<ContentCard> contentCards = contentService.getContentCardsPostedByUser(user);
            theModel.addAttribute("contentCards", contentCards);
            theModel.addAttribute("currentUser", user);
        }
        catch (Exception e){
            return "home";
        }
        return "user-profile/user-profile";
    }

    @GetMapping("/image/{userName}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("userName") String userName) {
        User user = userService.getByUserName(userName);
        if (user != null && user.getImage() != null) {
            byte[] imageData = user.getImage().getImageData();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
