package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.entity.VideoDto;
import com.hackWeb.hackWeb.service.UserService;
import com.hackWeb.hackWeb.service.UserVideoService;
import com.hackWeb.hackWeb.service.VideoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("video")
public class VideoController {

    private final UserService userService;
    private final VideoService videoService;
private final UserVideoService userVideoService;

    @Value("${video.storage.location}")
    private String videoStorageLocation;



    public VideoController(ResourceLoader resourceLoader, UserService userService, VideoService videoService, UserVideoService userVideoService) {
        this.userService = userService;
        this.videoService = videoService;
        this.userVideoService = userVideoService;
    }



    @GetMapping("/watch/{id}")
    public String watchVideo(@PathVariable("id") int videoId, Model model){

        User user = userService.getCurrentUser();

        model.addAttribute("user", user.getUserProfile());

        VideoDto videoDetails = userVideoService.getOne(videoId, user.getId());

        System.out.println("The video details are: " + videoDetails);
        model.addAttribute("videoDetails" , videoDetails);

        return "watchVideo";
    }

    @PostMapping("/save/{id}")
    public String saveVideo(@PathVariable("id") int videoId, Model model){
        User user = userService.getCurrentUser();

        userVideoService.setSave(videoId, user,true);
        model.addAttribute("user", user.getUserProfile());



        return "redirect:/video/watch/" + videoId + "?justSaved=true";
    }

    @PostMapping("/unsave/{id}")
    public String unSaveVideo(@PathVariable("id") int videoId, Model model){
        User user = userService.getCurrentUser();

        userVideoService.setSave(videoId, user,false);
        model.addAttribute("user", user.getUserProfile());



        return "redirect:/video/watch/" + videoId + "?justUnsaved=true";
    }

    @PostMapping("/markWatched/{id}")
    public String markVideo(@PathVariable("id") int videoId, Model model){
        User user = userService.getCurrentUser();

        userVideoService.setComplete(videoId, user,true);
        model.addAttribute("user", user.getUserProfile());



        return "redirect:/video/watch/" + videoId + "?justWatched=true";
    }

    @PostMapping("/unmarkWatched/{id}")
    public String unmarkVideo(@PathVariable("id") int videoId, Model model){
        User user = userService.getCurrentUser();

        userVideoService.setComplete(videoId, user,false);
        model.addAttribute("user", user.getUserProfile());



        return "redirect:/video/watch/" + videoId + "?justUnwatched=true";
    }


}