package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.entity.VideoDto;
import com.hackWeb.hackWeb.service.UserService;
import com.hackWeb.hackWeb.service.UserVideoService;
import com.hackWeb.hackWeb.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserVideoController {

    private final UserVideoService userVideoService;
    private final UserService userService;
    private final VideoService videoService;

    public UserVideoController(UserVideoService userVideoService, UserService userService, VideoService videoService) {
        this.userVideoService = userVideoService;
        this.userService = userService;
        this.videoService = videoService;
    }


    @GetMapping("/saved-videos/")
    public String getVideosSaved(Model model) {
        User user = userService.getCurrentUser();

        List<VideoDto> videosDto = videoService.getAllByUserId(user.getId());

        List<VideoDto> videosToShow = videosDto.stream()
                .filter(VideoDto::isSaved)
                .collect(Collectors.toList());

        model.addAttribute("videos", videosToShow);
        model.addAttribute("user", user.getUserProfile());

        return "saved-videos";
    }
}
