package com.example.communityapplication.controller;

import com.example.communityapplication.model.Community;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {

    @GetMapping("/")
    public String showHome() {

        return "home";
    }

    @GetMapping("/postForm")
    public String showPostFrom() {

        return "post-form";
    }

    @PostMapping("/createPost")
    public String showPostForm(Model theModel) {

        return "/";
    }
}









