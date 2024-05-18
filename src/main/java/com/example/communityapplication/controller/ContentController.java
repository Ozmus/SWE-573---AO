package com.example.communityapplication.controller;

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
@RequestMapping("/content")
public class ContentController {
    private CommunityService communityService;
    private UserRoleService userRoleService;
    private ContentTemplateService contentTemplateService;
    private ContentService contentService;
    private FieldService fieldService;
    private FieldValueService fieldValueService;


    @Autowired
    public ContentController(CommunityService communityService,
                             UserRoleService userRoleService,
                             ContentTemplateService contentTemplateService,
                             ContentService contentService,
                             FieldService fieldService,
                             FieldValueService fieldValueService) {
        this.communityService = communityService;
        this.userRoleService = userRoleService;
        this.contentTemplateService = contentTemplateService;
        this.contentService = contentService;
        this.fieldService = fieldService;
        this.fieldValueService = fieldValueService;
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
            communities.add(communityService.getByCommunityId(userRole.getId().getCommunityId()));
        }
        theModel.addAttribute("communities", communities);
        return "content/select-community";
    }

    @GetMapping("/selectContentTemplate")
    public String showContentTemplate(@RequestParam("name") String name, Model theModel, HttpSession session) {
        Community community = communityService.getByCommunityName(name);
        List<ContentTemplate> contentTemplates = contentTemplateService.getByCommunity(community);
        theModel.addAttribute("community", community);
        theModel.addAttribute("contentTemplates", contentTemplates);
        return "content/select-content-template";
    }

    @GetMapping("/createContent")
    public String showCreateForm(@RequestParam("contentTemplateId") String contentTemplateId,
                                 Model theModel,
                                 HttpSession session) {
        // Fetch content template and fields based on content template ID
        ContentTemplate contentTemplate = contentTemplateService.getById(Integer.parseInt(contentTemplateId));
        List<Field> fields = fieldService.getFieldsByContentTemplateId(Integer.parseInt(contentTemplateId));

        // Add content template and fields to the model
        theModel.addAttribute("contentTemplate", contentTemplate);
        theModel.addAttribute("fields", fields);
        theModel.addAttribute("contentForm", new ContentForm());

        return "content/create-content-form";
    }

    @PostMapping("/createContentSubmit")
    public String createContentSubmit(@RequestParam("contentTemplateId") String contentTemplateId,
                                      @ModelAttribute("contentForm") ContentForm contentForm,
                                      HttpSession session) {
        User user = (User) session.getAttribute("user");
        contentForm.setContentTemplate(contentTemplateService.getById(Integer.parseInt(contentTemplateId)));

        // Save the content and field values
        Content content = contentService.saveContent(contentForm, user);
        fieldValueService.saveFieldValues(contentForm, content);
        return "home";
    }
}