package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.*;
import com.hackWeb.hackWeb.entity.enums.VideoType;
import com.hackWeb.hackWeb.exception.*;
import com.hackWeb.hackWeb.repository.AttackRepository;
import com.hackWeb.hackWeb.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AttackService {

    private final AttackRepository attackRepository;

    public AttackService(AttackRepository attackRepository) {
        this.attackRepository = attackRepository;
    }

    public List<Attack> getAll() {
        return attackRepository.findAll();
    }

    public Attack getOneById(int id) {
        return attackRepository.findById(id).orElseThrow(() -> new RuntimeException("Attack not found"));
    }

    public List<Attack> search(String attackTitle, List<String> difficulties, List<String> typeAttacks) {

        System.out.println("These are the difficulties: " + difficulties + ". And this are the typeAttacks " + typeAttacks + ". And this is the attack " + attackTitle);
        return attackRepository.search(difficulties, typeAttacks, attackTitle);

    }

    public List<AttackDto> searchDto(String attackTitle, List<String> difficulties, List<String> typeAttacks, int userId) {

        List<IAttack> iAttacks = attackRepository.searchDto(difficulties, typeAttacks, attackTitle, userId);


        return iAttacks.stream()
                .map(this::convertToAttackDto)
                .collect(Collectors.toList());
    }

    private AttackDto convertToAttackDto(IAttack ia) {
        TypeAttack typeAttack = new TypeAttack(ia.getTypeAttackId(), ia.getTypeAttackName());
        boolean isSaved = ia.getIsSaved() != null && ia.getIsSaved() == 1;
        boolean isCompleted = ia.getIsCompleted() != null && ia.getIsCompleted() == 1;

        return new AttackDto(
                ia.getTotalStudentsCompleted(),
                ia.getId(),
                ia.getTitle(),
                ia.getDifficulty(),
                typeAttack,
                isSaved,
                isCompleted,
                ia.getDescription(),
                ia.getPostedDate(),
                ia.getPreVideoFile(),
                ia.getSolutionVideoFile(),
                ia.getQuestion(),
                ia.getAnswer(),
                ia.getDockerImageName());
    }

    public AttackDto getOneByAttackIdAndUserId(int attackId, int userId) {

        IAttack iAttack = attackRepository.getOneDtoById(attackId, userId);

        return convertToAttackDto(iAttack);
    }

    public Attack getOneByDockerImageName(String dockerImageName) {
        return attackRepository.findByDockerImageName(dockerImageName);
    }

    @Transactional
    public void save(Attack attack) {
        attackRepository.save(attack);
    }
//
//    @Transactional
    public void updateAttackWithVideos(Attack attack, MultipartFile preVideoFile, MultipartFile solutionVideoFile) {
        try {

            List<Video> existingVideos = attackRepository.findById(attack.getId()).orElseThrow(() -> new RuntimeException("No attack with that id")).getVideos();

            String uploadDir = "videos/attack/" + attack.getId();

            removeVideosIfExists(preVideoFile, solutionVideoFile, uploadDir, existingVideos);

            List<Video> videosToSave = processVideoFiles(attack, preVideoFile, solutionVideoFile);
            existingVideos.addAll(videosToSave);
            attack.setVideos(existingVideos);

            attackRepository.save(attack);

            saveVideoFiles(uploadDir, preVideoFile, solutionVideoFile);

        } catch (DataIntegrityViolationException e) {
            throw new ImageAttackExistsOnUpdateException("That image is already used by another attack", e);
        } catch (Exception exc) {
            throw new GeneralExceptionWithContext("Unexpected Exception", exc, "edit-attack");
        }


    }

    public void delete(int attackId) {
        if (!attackRepository.existsById(attackId)) {
            throw new AttackNotFoundException("The attack with id: " + attackId + " wasn't found");
        }
        attackRepository.deleteById(attackId);
    }

    private void removeVideosIfExists(MultipartFile preVideoFile, MultipartFile solutionVideoFile, String uploadDir, List<Video> existingVideos) {

        existingVideos.removeIf(video -> video.getVideoFile() == null);

        if (preVideoFile != null && !preVideoFile.isEmpty()) {
            existingVideos.removeIf(video -> {
                if (video.getType() == VideoType.PRE) {
                    FileUploadUtil.deleteFile(uploadDir, video.getVideoFile());
                    return true;
                }
                return false;
            });
        }

        if (solutionVideoFile != null && !solutionVideoFile.isEmpty()) {
            existingVideos.removeIf(video -> {
                if (video.getType() == VideoType.SOLUTION) {
                    FileUploadUtil.deleteFile(uploadDir, video.getVideoFile());
                    return true;
                }
                return false;
            });
        }
    }

    private void saveVideoFiles(String uploadDir, MultipartFile preVideoFile, MultipartFile solutionVideoFile) {
        try {
            if (preVideoFile != null && !preVideoFile.isEmpty()) {
                String preFilename = StringUtils.cleanPath(Objects.requireNonNull(preVideoFile.getOriginalFilename()));
                FileUploadUtil.saveFile(uploadDir, preFilename, preVideoFile);
            }
            if (solutionVideoFile != null && !solutionVideoFile.isEmpty()) {
                String solutionFilename = StringUtils.cleanPath(Objects.requireNonNull(solutionVideoFile.getOriginalFilename()));
                FileUploadUtil.saveFile(uploadDir, solutionFilename, solutionVideoFile);
            }
        } catch (IOException e) {
            throw new PhisicallySaveVideoException("There was an error trying to save the physical video locally", e);
        }
    }

    private List<Video> processVideoFiles(Attack attack, MultipartFile preVideoFile, MultipartFile solutionVideoFile) {
        List<Video> videosToSave = new ArrayList<>();

        if (preVideoFile != null && !preVideoFile.isEmpty()) {
            String preFilename = StringUtils.cleanPath(Objects.requireNonNull(preVideoFile.getOriginalFilename()));
            Video preVideo = new Video(attack, VideoType.PRE);
            preVideo.setDifficulty(attack.getDifficulty());
            preVideo.setTypeAttack(attack.getTypeAttack());
            preVideo.setVideoFile(preFilename);
            videosToSave.add(preVideo);
        }

        if (solutionVideoFile != null && !solutionVideoFile.isEmpty()) {
            String solutionFilename = StringUtils.cleanPath(Objects.requireNonNull(solutionVideoFile.getOriginalFilename()));
            Video solutionVideo = new Video(attack, VideoType.SOLUTION);
            solutionVideo.setDifficulty(attack.getDifficulty());
            solutionVideo.setTypeAttack(attack.getTypeAttack());
            solutionVideo.setVideoFile(solutionFilename);
            videosToSave.add(solutionVideo);
        }

        return videosToSave;
    }


    public void addAttackWithVideos(Attack attack, MultipartFile preVideoFile, MultipartFile solutionVideoFile) {
        try {
            Attack savedAttack = attackRepository.save(attack);

        } catch (DataIntegrityViolationException e) {
            throw new ImageAttackExistsOnCreationException("That image is already used by another attack", e);
        }

        String uploadDir = "videos/attack/" + attack.getId();
        List<Video> videosToSave = processVideoFiles(attack, preVideoFile, solutionVideoFile);
        attack.setVideos(videosToSave);

        saveVideoFiles(uploadDir, preVideoFile, solutionVideoFile);


    }
}
