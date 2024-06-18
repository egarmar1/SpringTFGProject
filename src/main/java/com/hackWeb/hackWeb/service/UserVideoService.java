package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.*;
import com.hackWeb.hackWeb.repository.UserVideoRepository;
import com.hackWeb.hackWeb.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class UserVideoService {

    private final UserVideoRepository userVideoRepository;
    private final VideoRepository videoRepository;

    public UserVideoService(UserVideoRepository userVideoRepository, VideoRepository videoRepository) {
        this.userVideoRepository = userVideoRepository;
        this.videoRepository = videoRepository;
    }

    public void setSave(int videoId, User user, boolean save) {


        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));


        UserVideo userVideo = userVideoRepository.findByUserIdAndVideoId(user.getId(),videoId).orElse(new UserVideo());
        userVideo.setVideo(video);
        userVideo.setUser(user);
        userVideo.setSaved(save);

        userVideoRepository.save(userVideo);
    }

    public void setComplete(int videoId, User user, boolean completed) {

        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));


        UserVideo userVideo = userVideoRepository.findByUserIdAndVideoId(user.getId(),videoId).orElse(new UserVideo());
        userVideo.setVideo(video);
        userVideo.setUser(user);
        userVideo.setCompleted(completed);

        userVideoRepository.save(userVideo);
    }
}
