package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.*;
import com.hackWeb.hackWeb.repository.AttackRepository;
import com.hackWeb.hackWeb.repository.UserRepository;
import com.hackWeb.hackWeb.repository.UserVideoRepository;
import com.hackWeb.hackWeb.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class UserVideoService {

    private final UserVideoRepository userVideoRepository;
    private final VideoRepository videoRepository;
    private final AttackRepository attackRepository;
    private final UserRepository userRepository;

    public UserVideoService(UserVideoRepository userVideoRepository, VideoRepository videoRepository, AttackRepository attackRepository, UserRepository userRepository) {
        this.userVideoRepository = userVideoRepository;
        this.videoRepository = videoRepository;
        this.attackRepository = attackRepository;
        this.userRepository = userRepository;
    }

    public VideoDto getOne(int videoId, int userId){
        IVideo iVideo = userVideoRepository.getOneByVideoIdAndUserId(videoId,userId);

        if(iVideo == null){
            Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("There is not video with the id:" + videoId));
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            userVideoRepository.save(new UserVideo(video,user,false,false));
            iVideo = userVideoRepository.getOneByVideoIdAndUserId(videoId,userId);
        }

        return  convertToVideoDto(iVideo);
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

    private VideoDto convertToVideoDto(IVideo ia){
        TypeAttack typeAttack = new TypeAttack(ia.getTypeAttackId(),ia.getTypeAttackName());
        Attack attack = attackRepository.findById(ia.getAttackId()).orElseThrow(() -> new RuntimeException("Attack not found"));
//        boolean isSaved = ia.getIsSaved() != null && ia.getIsSaved() == 1;
//        boolean isCompleted = ia.getIsCompleted() != null && ia.getIsCompleted() == 1;

        return new VideoDto(ia.getId(),ia.getTitle(),ia.getDifficulty(),ia.getVideoFile(),attack, typeAttack, ia.getIsSaved(),ia.getIsCompleted());

    }
}
