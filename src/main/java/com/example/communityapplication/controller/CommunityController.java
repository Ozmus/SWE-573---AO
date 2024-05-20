package com.example.communityapplication.controller;


import com.example.communityapplication.enums.Role;
import com.example.communityapplication.model.*;
import com.example.communityapplication.model.embedded.keys.UserRolesId;
import com.example.communityapplication.service.CommunityService;
import com.example.communityapplication.service.UserRoleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
	public String showCommunityDetails(@RequestParam("name") String name, Model theModel, HttpSession session) {
		Community community = communityService.getByCommunityName(name);
		theModel.addAttribute("community", community);
		theModel.addAttribute("isMember", communityService.isMember(community, (User)session.getAttribute("user")));
		return "community/community-details";
	}
	@GetMapping("/create")
	public String showCreateCommunityForm(Model theModel) {
		
		theModel.addAttribute("newCommunity", new WebCommunity("", "", false));
		
		return "community/community-form";
	}

	@PostMapping("/join")
	public String joinCommunity(@RequestParam("name") String name, Model theModel, HttpSession session) {
		Community community = communityService.getByCommunityName(name);

		theModel.addAttribute("community", community);
		communityService.joinCommunity(community, (User)session.getAttribute("user"));
		theModel.addAttribute("isMember", communityService.isMember(community, (User)session.getAttribute("user")));
		return "community/community-details";
	}
	@PostMapping("/leave")
	public String leaveCommunity(@RequestParam("name") String name, Model theModel, HttpSession session) {
		Community community = communityService.getByCommunityName(name);

		theModel.addAttribute("community", community);
		try {
			communityService.leaveCommunity(community, (User) session.getAttribute("user"));
		}catch (Exception e){
			theModel.addAttribute("isMember", communityService.isMember(community, (User)session.getAttribute("user")));
			return "community/community-details";
		}
		return this.showCommunityPage(theModel);
	}

	@PostMapping("/processCreateCommunityForm")
	public String processCreateCommunityForm(
			@Valid @ModelAttribute("newCommunity") WebCommunity theWebCommunity,
			BindingResult theBindingResult,
			HttpSession session,
			Model theModel) {

		String communityName = theWebCommunity.getName();
		Community community = new Community(communityName, theWebCommunity.getDescription(), false);
		// form validation
		if (theBindingResult.hasErrors()){
			return "community/community-form";
		}

		// Check if image file is provided
		if (!theWebCommunity.getImage().isEmpty()) {
			try {
				// Convert image file data to byte array
				byte[] imageData = theWebCommunity.getImage().getBytes();

				// Create Image object and set image data
				Image image = new Image();
				image.setImageData(imageData);

				// Set Image object in Community
				community.setImage(image);
			} catch (IOException e) {
				e.printStackTrace();
				// Handle error
				return "community/community-form";
			}
		}

		if (communityService.isExist(communityName)){
			theModel.addAttribute("newCommunity", new WebCommunity("", "", false));
			theModel.addAttribute("communityCreationError", "Community name already exists.");

			logger.warning("Community name already exists.");
			return "community/community-form";
		}

		try {
			communityService.createCommunity(community, (User)session.getAttribute("user"));
		} catch (Exception e){
			theModel.addAttribute("newCommunity", new WebCommunity("", "", false));
			theModel.addAttribute("communityCreationError", "Community name already exists.");

			logger.warning("Community name already exists.");
			return "community/community-form";
		}

		logger.info("Successfully created community: " + communityName);

		return this.showCommunityPage(theModel);
	}


	@GetMapping("/searchCommunitySubmit")
	public String searchContent(@RequestParam("keyword") String keyword,
								HttpSession session,
								Model theModel) {
		try {
			List<Community> communities = communityService.searchCommunities(keyword);
			theModel.addAttribute("communities", communities);
		}
		catch (Exception e){
			theModel.addAttribute("communities", new ArrayList<ContentCard>());
		}
		return "community/community-page";
	}

	@GetMapping("/image/{communityId}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(@PathVariable("communityId") int communityId) {
		Community community = communityService.getByCommunityId(communityId);
		if (community != null && community.getImage() != null) {
			byte[] imageData = community.getImage().getImageData();
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
