package com.example.communityapplication.controller;

import com.example.communityapplication.model.Content;
import com.example.communityapplication.model.ContentCard;
import com.example.communityapplication.model.User;
import com.example.communityapplication.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {

    private ContentService contentService;

    @Autowired
    public ApplicationController(ContentService contentService) {
        this.contentService = contentService;
    }
    @GetMapping("/")
    public String showHome(HttpSession session,
                           Model theModel) {
        try {
            List<ContentCard> contentCards = contentService.getContentCardsByUser((User) session.getAttribute("user"));
            theModel.addAttribute("contentCards", contentCards);
        }
        catch (Exception e){
            theModel.addAttribute("contentCards", new ArrayList<ContentCard>());
        }
        return "home";
    }

    @PostMapping("/searchContentSubmit")
    public String searchContent(@RequestParam("keyword") String keyword,
                                HttpSession session,
                                Model theModel) {
        try {
            List<ContentCard> contentCards = contentService.search(keyword);
            theModel.addAttribute("contentCards", contentCards);
        }
        catch (Exception e){
            theModel.addAttribute("contentCards", new ArrayList<ContentCard>());
        }
        return "home";
    }
}









