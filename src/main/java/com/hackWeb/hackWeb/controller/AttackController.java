package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.*;
import com.hackWeb.hackWeb.entity.enums.VideoType;
import com.hackWeb.hackWeb.exception.ApiRequestException;
import com.hackWeb.hackWeb.service.AttackService;
import com.hackWeb.hackWeb.service.TypeAttackService;
import com.hackWeb.hackWeb.service.UserAttackService;
import com.hackWeb.hackWeb.service.UserService;
import com.hackWeb.hackWeb.util.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
public class AttackController {

    private final AttackService attackService;
    private final UserService userService;
    private final UserAttackService userAttackService;
    private final TypeAttackService typeAttackService;

    public AttackController(AttackService attackService, UserService userService, UserAttackService userAttackService, TypeAttackService typeAttackService) {
        this.attackService = attackService;
        this.userService = userService;
        this.userAttackService = userAttackService;
        this.typeAttackService = typeAttackService;
    }


    @GetMapping("/attack-details/{id}")
    public String attackDetails(@PathVariable("id") int attackId, Model model) {



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userService.getCurrentUser();

            if (user != null) {
                model.addAttribute("user", user.getUserProfile());
            }

            AttackDto attackDto = attackService.getOneByAttackIdAndUserId(attackId, user.getId());


            model.addAttribute("attackDetails", attackDto);

        }
        return "attack-details";
    }

    @PostMapping("/attack-details/save/{id}")
    public String saveAttack(@PathVariable("id") int attackId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.getCurrentUser();

            userAttackService.setSave(attackId, user, true);
            model.addAttribute("user", user.getUserProfile());
        }

        return "redirect:/dashboard/?justSaved=true";
    }

    @PostMapping("/attack-details/unsave/{id}")
    public String unsaveAttack(@PathVariable("id") int attackId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.getCurrentUser();

            userAttackService.setSave(attackId, user, false);
            model.addAttribute("user", user.getUserProfile());
        }

        return "redirect:/dashboard/?justUnsaved=true";
    }

    @PostMapping("/attack-details/complete/{id}")
    public ResponseEntity<Map<String,String>> completeAttack(@PathVariable("id") int attackId, Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {


            String username = authentication.getName();
            User user = userService.getCurrentUser();
            Attack attack = attackService.getOneById(attackId);

            Integer solutionVideoId = attack.getVideos().stream()
                    .filter(video -> video.getType() == VideoType.SOLUTION)
                    .map(Video::getId)
                    .findFirst().orElseThrow(() -> new RuntimeException("There is no solution video"));


            userAttackService.setComplete(attackId, user, true);
            model.addAttribute("user", user.getUserProfile());
            model.addAttribute("showModal", true); // Agregar atributo para mostrar el modal

            Map<String,String> response = new HashMap<>();

            response.put("solutionVideoId",solutionVideoId.toString());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PostMapping("/attack-details/uncomplete/{id}")
    public String uncompleteAttack(@PathVariable("id") int attackId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.getCurrentUser();

            userAttackService.setComplete(attackId, user, false);
            model.addAttribute("user", user.getUserProfile());
        }

        return "redirect:/dashboard/?justUncompleted=true";
    }

    @GetMapping("/attack/add")
    public String addAttackView(Model model) {
        UserProfile userProfile = userService.getCurrentUser().getUserProfile();
        Attack attack = new Attack();
        List<TypeAttack> typeAttacks = typeAttackService.getAll();

        model.addAttribute("attack", attack);
        model.addAttribute("typeAttacks", typeAttacks);
        model.addAttribute("user", userProfile);

        return "add-attack";
    }


        @PostMapping("/attack/addNew")
        public String addNewAttack(Attack attack,
                                   @RequestParam("preVideoFile") MultipartFile preVideoFile,
                                   @RequestParam("solutionVideoFile") MultipartFile solutionVideoFile) {


            List<Video> videosToSave = new ArrayList<>();

            if(preVideoFile != null && !preVideoFile.isEmpty()){
                String preFilename = StringUtils.cleanPath(Objects.requireNonNull(preVideoFile.getOriginalFilename()));
                Video preVideo = new Video(attack, VideoType.PRE);
                preVideo.setDifficulty(attack.getDifficulty());
                preVideo.setTitle(attack.getVideos().get(0).getTitle());
                preVideo.setTypeAttack(attack.getTypeAttack());
                preVideo.setVideoFile(preFilename);
                videosToSave.add(preVideo);
            }

            if(solutionVideoFile != null && !solutionVideoFile.isEmpty()){
                String solutionFilename = StringUtils.cleanPath(Objects.requireNonNull(solutionVideoFile.getOriginalFilename()));
                Video solutionVideo = new Video(attack, VideoType.SOLUTION);
                solutionVideo.setDifficulty(attack.getDifficulty());
                solutionVideo.setTitle(attack.getVideos().get(1).getTitle());
                solutionVideo.setTypeAttack(attack.getTypeAttack());
                solutionVideo.setVideoFile(solutionFilename);
                videosToSave.add(solutionVideo);
            }


            attack.setVideos(videosToSave);
            attack.setPosted_date(new Date());
            attackService.save(attack);

            String uploadDir = "videos/attack/" + attack.getId();

            try{
                if(preVideoFile != null && !preVideoFile.isEmpty()) {
                    String preFilename = StringUtils.cleanPath(Objects.requireNonNull(preVideoFile.getOriginalFilename()));
                    FileUploadUtil.saveFile(uploadDir, preFilename, preVideoFile);
                }
                if(solutionVideoFile != null && !solutionVideoFile.isEmpty()) {
                    String solutionFilename = StringUtils.cleanPath(Objects.requireNonNull(solutionVideoFile.getOriginalFilename()));
                    FileUploadUtil.saveFile(uploadDir, solutionFilename, solutionVideoFile);
                }
            }catch (Exception exc){
                exc.printStackTrace();
            }


            return "redirect:/dashboard/?attackSaved=true";
        }
}
