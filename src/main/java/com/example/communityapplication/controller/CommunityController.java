package com.example.communityapplication.controller;


import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.Community;
import com.example.communityapplication.model.User;
import com.example.communityapplication.model.UserRole;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
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

import java.util.logging.Logger;

@Controller
@RequestMapping("/community")
public class CommunityController {

	private Logger logger = Logger.getLogger(getClass().getName());

    private CommunityService communityService;
	private UserRoleService userRoleService;

	@Autowired
	public CommunityController(CommunityService communityService, UserRoleService userRoleService) {
		this.communityService = communityService;
		this.userRoleService = userRoleService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showCommunityPage")
	public String showCommunityPage() {
		return "community/community-page";
	}

	@GetMapping("/showCreateCommunityForm")
	public String showCreateCommunityForm(Model theModel) {
		
		theModel.addAttribute("newCommunity", new Community("", "", "", false));
		
		return "community/community-form";
	}

	@PostMapping("/processCreateCommunityForm")
	public String processCreateCommunityForm(
			@Valid @ModelAttribute("newCommunity") Community theCommunity,
			BindingResult theBindingResult,
			HttpSession session, Model theModel) {

		String communityName = theCommunity.getName();
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "community/community-form";
		 }

		// check the database if community already exists
        Community existing = communityService.getByCommunityName(communityName);
        if (existing != null){
        	theModel.addAttribute("newCommunity", new Community("", "", "", false));
			theModel.addAttribute("communityCreationError", "Community name already exists.");

			logger.warning("Community name already exists.");
        	return "community/community-form";
        }
        
        // save community
        communityService.save(theCommunity);

		//create owner relationship
		User currentUser = (User)session.getAttribute("user");
		Community createdCommunity = communityService.getByCommunityName(theCommunity.getName());
		userRoleService.save(new UserRole(new UserRolesId(currentUser.getId(), createdCommunity.getId()), Role.OWNER));
        
        logger.info("Successfully created community: " + communityName);

        return "community/community-page";
	}
}
