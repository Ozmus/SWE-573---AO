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

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/community")
public class CommunityController {

	private Logger logger = Logger.getLogger(getClass().getName());

    private CommunityService communityService;

	@Autowired
	public CommunityController(CommunityService communityService) {
		this.communityService = communityService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping()
	public String showCommunityPage(Model theModel) {
		List<Community> communities = communityService.getAllCommunities();
		theModel.addAttribute("communities", communities);
		return "community/community-page";
	}
	@GetMapping("/details")
	public String showCommunityDetails(@RequestParam("name") String name, Model theModel) {
		Community community = communityService.getByCommunityName(name);
		theModel.addAttribute("community", community);
		return "community/community-details";
	}
	@GetMapping("/create")
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

        if (communityService.isExist(communityName)){
        	theModel.addAttribute("newCommunity", new Community("", "", "", false));
			theModel.addAttribute("communityCreationError", "Community name already exists.");

			logger.warning("Community name already exists.");
        	return "community/community-form";
        }
        
        // save community
        communityService.createCommunity(theCommunity, (User)session.getAttribute("user"));

        logger.info("Successfully created community: " + communityName);

        return "community/community-page";
	}
}
