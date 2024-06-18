package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.Country;
import com.hackWeb.hackWeb.entity.UserProfile;
import com.hackWeb.hackWeb.service.CountryService;
import com.hackWeb.hackWeb.service.UserProfileService;
import com.hackWeb.hackWeb.service.UserService;
import com.hackWeb.hackWeb.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("user-profile")
public class UserProfileController {

    private final UserService userService;

    private final CountryService countryService;

    private final UserProfileService userProfileService;

    public UserProfileController(UserService userService, CountryService countryService, UserProfileService userProfileService) {
        this.userService = userService;
        this.countryService = countryService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/view")
    public String viewProfile(Model model){

        UserProfile userProfile = userService.getCurrentUser().getUserProfile();
        List<Country> countries = countryService.getAll();


        model.addAttribute("profile", userProfile);
        model.addAttribute("countries", countries);

        return "user-profile";
    }
    @PostMapping("/update")
    public String updateProfile(@Valid UserProfile userProfile, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile image){

        if(bindingResult.hasErrors()){
            //UserProfile previousUserProfile = userService.getCurrentUser().getUserProfile();
            List<Country> countries = countryService.getAll();


            model.addAttribute("profile", userProfile);
            model.addAttribute("countries", countries);

            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Validation error: " + error);
            });




            return "user-profile";
        }

        String imageName = "";

        if(!Objects.equals(image.getOriginalFilename(), "")){
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            userProfile.setProfilePhoto(imageName);
        }

        //UserProfile currentProfile = userProfileService.getCurrentProfile();
        System.out.println("UserProfile is: " + userProfile);
        userProfile.setUser(userService.getCurrentUser()); // quizás ni hace falta esto, habrá que verlo
        userProfileService.update(userProfile);


        String uploadDir ="photos/student/" + userProfile.getId();


        try {
            FileUploadUtil.saveFile(uploadDir, imageName, image);
        } catch (Exception exc){
            exc.printStackTrace();
        }


        return "redirect:/dashboard/";
    }

}
