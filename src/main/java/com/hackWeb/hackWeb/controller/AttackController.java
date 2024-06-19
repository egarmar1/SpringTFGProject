package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.*;
import com.hackWeb.hackWeb.entity.enums.VideoType;
import com.hackWeb.hackWeb.service.AttackService;
import com.hackWeb.hackWeb.service.TypeAttackService;
import com.hackWeb.hackWeb.service.UserAttackService;
import com.hackWeb.hackWeb.service.UserService;
import com.hackWeb.hackWeb.util.FileUploadUtil;
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

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public String completeAttack(@PathVariable("id") int attackId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.getCurrentUser();

            userAttackService.setComplete(attackId, user, true);
            model.addAttribute("user", user.getUserProfile());
            model.addAttribute("showModal", true); // Agregar atributo para mostrar el modal
        }

        return "redirect:/video/watch";
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

            Video preVideo = attack.getVideos().stream()
                    .filter( video -> video.getType() == VideoType.PRE).findFirst().orElse(new Video(attack, VideoType.PRE));

            Video solutionVideo = attack.getVideos().stream()
                    .filter( video -> video.getType() == VideoType.SOLUTION).findFirst().orElse(new Video(attack, VideoType.SOLUTION));




            preVideo.setDifficulty(attack.getDifficulty());
            solutionVideo.setDifficulty(attack.getDifficulty());

            String preFileName = "";
            String solutionFileName = "";
            if(!Objects.equals(preVideoFile.getOriginalFilename(), "")){
                preFileName = StringUtils.cleanPath(Objects.requireNonNull(preVideoFile.getOriginalFilename()));
                preVideo.setVideoFile(preFileName);
            }

            if(!Objects.equals(solutionVideoFile.getOriginalFilename(), "")){
                solutionFileName = StringUtils.cleanPath(Objects.requireNonNull(solutionVideoFile.getOriginalFilename()));
                solutionVideo.setVideoFile(solutionFileName);
            }

            attack.setPosted_date(new Date());


            System.out.println("El attack es: " + attack);
            System.out.println("El preVideo es: " + preVideo.getAttack());
            System.out.println("El solutionVideo es: " + solutionVideo);


            attackService.save(attack);

            String uploadDir = "videos/attack/" + attack.getId();

            try{
                FileUploadUtil.saveFile(uploadDir,preFileName,preVideoFile);
                FileUploadUtil.saveFile(uploadDir,solutionFileName,solutionVideoFile);
            }catch (Exception exc){
                exc.printStackTrace();
            }


            return "dashboard";
        }


}
