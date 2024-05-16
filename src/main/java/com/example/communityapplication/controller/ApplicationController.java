package com.example.communityapplication.controller;

import com.example.communityapplication.model.*;
import com.example.communityapplication.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {

    private ContentService contentService;
    private FieldValueService fieldValueService;
    private ImageService imageService;

    @Autowired
    public ApplicationController(ContentService contentService,
                                 FieldValueService fieldValueService,
                                 ImageService imageService) {
        this.contentService = contentService;
        this.fieldValueService=fieldValueService;
        this.imageService=imageService;
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

    @GetMapping("/image/{fieldId}/{contentId}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("fieldId") int fieldId, @PathVariable("contentId") int contentId) {
        FieldValue fieldValue = fieldValueService.getFieldValueByFieldAndContentId(contentId, fieldId);
        if (fieldValue != null && fieldValue.getValue() != null) {
            byte[] imageData = imageService.getById(Integer.parseInt(fieldValue.getValue())).getImageData();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}









