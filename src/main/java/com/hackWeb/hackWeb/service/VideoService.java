package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.*;
import com.hackWeb.hackWeb.repository.AttackRepository;
import com.hackWeb.hackWeb.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final AttackRepository attackRepository;

    public VideoService(VideoRepository videoRepository, AttackRepository attackRepository) {
        this.videoRepository = videoRepository;
        this.attackRepository = attackRepository;
    }

    public VideoDto getOneByVideoIdAndUserId(int videoId, int userId){
        IVideo iVideo = videoRepository.getOneByVideoIdAndUserId(videoId,userId);

        return  convertToVideoDto(iVideo);
    }

    private VideoDto convertToVideoDto(IVideo ia){
        TypeAttack typeAttack = new TypeAttack(ia.getTypeAttackId(),ia.getTypeAttackName());
        Attack attack = attackRepository.findById(ia.getAttackId()).orElseThrow(() -> new RuntimeException("Attack not found"));
//        boolean isSaved = ia.getIsSaved() != null && ia.getIsSaved() == 1;
//        boolean isCompleted = ia.getIsCompleted() != null && ia.getIsCompleted() == 1;

        return new VideoDto(ia.getId(),ia.getTitle(),ia.getDifficulty(),ia.getVideoFile(),attack, typeAttack, ia.getIsSaved(),ia.getIsCompleted());

    }
}
