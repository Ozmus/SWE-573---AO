package com.example.communityapplication.controller;

import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/management")
public class ManagementController {
    private CommunityService communityService;
    private UserRoleService userRoleService;
    private UserService userService;
    private ContentTemplateService contentTemplateService;
    private FieldService fieldService;

    @Autowired
    public ManagementController(CommunityService communityService,
                                UserRoleService userRoleService,
                                ContentTemplateService contentTemplateService,
                                UserService userService,
                                FieldService fieldService) {
        this.communityService = communityService;
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

    @GetMapping("/selectUser")
    public String showUsers(@RequestParam("name") String name, Model theModel, HttpSession session) {
        Community community = communityService.getByCommunityName(name);
        User user = (User)session.getAttribute("user");
        List<User> users = userService.getUsersForManagement(community.getId(), user);

        theModel.addAttribute("community", community);
        theModel.addAttribute("users", users);
        return "management/user-operation/select-users";
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
        return "management/user-operation/user-details";
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

    @PostMapping("/userDetails/depromote")
    public String depromoteUser(@RequestParam("communityName") String communityName,
                              @RequestParam("userName") String userName,
                              Model theModel,
                              HttpSession session) {
        Community community = communityService.getByCommunityName(communityName);
        User userToPromote = userService.getByUserName(userName);
        User currentUser = (User)session.getAttribute("user");
        Role currentUserRole = userRoleService.getRoleByUserAndCommunityId(new UserRolesId(currentUser.getId(), community.getId())).getRole();
        userRoleService.depromoteUser(currentUserRole, userToPromote, community);

        return this.showUsers(communityName, theModel, session);
    }

    @GetMapping("/selectContentTemplate")
    public String showContentTemplate(@RequestParam("name") String name, Model theModel, HttpSession session) {
        Community community = communityService.getByCommunityName(name);
        List<ContentTemplate> contentTemplates = contentTemplateService.getByCommunity(community);
        theModel.addAttribute("community", community);
        theModel.addAttribute("contentTemplates", contentTemplates);
        return "management/content-template-operation/select-content-template";
    }

    @GetMapping("/contentTemplate/create")
    public String showCreateContentTemplateForm(@RequestParam("communityName") String communityName,
                                                @RequestParam(value = "contentTemplateId", required = false) String contentTemplateId,
                                                Model theModel) {
        Community community = communityService.getByCommunityName(communityName);
        ContentTemplate contentTemplate;
        if(contentTemplateId == null){
            contentTemplate = contentTemplateService.save(new ContentTemplate("", community));
        }else{
            contentTemplate = contentTemplateService.getById(Integer.parseInt(contentTemplateId));
        }
        List<Field> fields = fieldService.getFieldsByContentTemplateId(contentTemplate.getId());
        theModel.addAttribute("community", community);
        theModel.addAttribute("newContentTemplate", contentTemplate);
        theModel.addAttribute("fields", fields);
        return "management/content-template-operation/new-content-template-form";
    }

    @PostMapping("/contentTemplate/newContentTemplate")
    public String addNewContentTemplate(@Valid @ModelAttribute("newContentTemplate") ContentTemplate newContentTemplate,
                                @RequestParam("contentTemplateId") String contentTemplateId,
                                Model theModel,
                                HttpSession session,
                                BindingResult theBindingResult) {
        // form validation
        if (theBindingResult.hasErrors()){
            return "management/content-template-operation/new-content-template-form";
        }
        ContentTemplate contentTemplate = contentTemplateService.getById(Integer.parseInt(contentTemplateId));
        contentTemplate.setName(newContentTemplate.getName());
        contentTemplateService.save(contentTemplate);

        return this.showContentTemplate(contentTemplate.getCommunity().getName(), theModel, session);
    }

    @GetMapping("/contentTemplate/newField")
    public String showCreateFieldForm(@RequestParam(value = "contentTemplateId") String contentTemplateId,
                                      Model theModel) {
        ContentTemplate contentTemplate = contentTemplateService.getById(Integer.parseInt(contentTemplateId));
        Field newField = fieldService.save(new Field("","", contentTemplate));

        theModel.addAttribute("contentTemplate", contentTemplate);
        theModel.addAttribute("newField", newField);
        return "management/content-template-operation/field/field-form";
    }

    @PostMapping("/contentTemplate/addField")
    public String addNewField(@Valid @ModelAttribute("newField") Field newField,
                              @RequestParam(value = "contentTemplateId") String contentTemplateId,
                              Model theModel,
                              HttpSession session,
                              BindingResult theBindingResult) {
        // form validation
        if (theBindingResult.hasErrors()){
            return "management/content-template-operation/new-content-template-form";
        }
        ContentTemplate contentTemplate = contentTemplateService.getById(Integer.parseInt(contentTemplateId));
        newField.setContentTemplate(contentTemplate);
        fieldService.save(newField);

        return this.showCreateContentTemplateForm(contentTemplate.getCommunity().getName(), contentTemplateId, theModel);
    }
}