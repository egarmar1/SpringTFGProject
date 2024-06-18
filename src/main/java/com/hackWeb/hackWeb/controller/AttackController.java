package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.entity.AttackDto;
import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.service.AttackService;
import com.hackWeb.hackWeb.service.UserAttackService;
import com.hackWeb.hackWeb.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AttackController {

    private final AttackService attackService;
    private final UserService userService;
    private final UserAttackService userAttackService;

    public AttackController(AttackService attackService, UserService userService, UserAttackService userAttackService) {
        this.attackService = attackService;
        this.userService = userService;
        this.userAttackService = userAttackService;
    }


    @GetMapping("/attack-details/{id}")
    public String attackDetails(@PathVariable("id") int attackId, Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = userService.getCurrentUser();

            if(user != null){
                model.addAttribute("user", user.getUserProfile());
            }

            AttackDto attackDto = attackService.getOneByAttackIdAndUserId(attackId, user.getId());


            model.addAttribute("attackDetails", attackDto);

        }

        return "attack-details";
    }

    @PostMapping("/attack-details/save/{id}")
    public String saveAttack(@PathVariable("id") int attackId, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user = userService.getCurrentUser();

            userAttackService.setSave(attackId, user,true);
            model.addAttribute("user", user.getUserProfile());
        }

        return "redirect:/dashboard/?justSaved=true";
    }
    @PostMapping("/attack-details/unsave/{id}")
    public String unsaveAttack(@PathVariable("id") int attackId, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user = userService.getCurrentUser();

            userAttackService.setSave(attackId, user,false);
            model.addAttribute("user", user.getUserProfile());
        }

        return "redirect:/dashboard/?justUnsaved=true";
    }

    @PostMapping("/attack-details/complete/{id}")
    public String completeAttack(@PathVariable("id") int attackId, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user = userService.getCurrentUser();

            userAttackService.setComplete(attackId, user,true);
            model.addAttribute("user", user.getUserProfile());
            model.addAttribute("showModal", true); // Agregar atributo para mostrar el modal
        }

        return "redirect:/video/watch";
    }


    @PostMapping("/attack-details/uncomplete/{id}")
    public String uncompleteAttack(@PathVariable("id") int attackId, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user = userService.getCurrentUser();

            userAttackService.setComplete(attackId, user,false);
            model.addAttribute("user", user.getUserProfile());
        }

        return "redirect:/dashboard/?justUncompleted=true";
    }
}
