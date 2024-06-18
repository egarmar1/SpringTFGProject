package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.entity.AttackDto;
import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.entity.UserAttack;
import com.hackWeb.hackWeb.service.AttackService;
import com.hackWeb.hackWeb.service.TypeAttackService;
import com.hackWeb.hackWeb.service.UserAttackService;
import com.hackWeb.hackWeb.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserAttackController {


    private final AttackService attackService;
    private final TypeAttackService typeAttackService;
    private final UserService userService;
    private final UserAttackService userAttackService;

    public UserAttackController(AttackService attackService, TypeAttackService typeAttackService, UserService userService, UserAttackService userAttackService) {
        this.attackService = attackService;
        this.typeAttackService = typeAttackService;
        this.userService = userService;
        this.userAttackService = userAttackService;
    }

    @GetMapping("/saved-attacks/")
    public String savedJobs(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = userService.getCurrentUser();

        String attackBar = "";
        String easy = "Easy";
        String medium = "Medium";
        String hard = "Hard";
        List<String> namesTypeAttacks = typeAttackService.getAllNames();

        List<AttackDto> attacksDtoSearched = attackService.searchDto(attackBar, Arrays.asList(easy, medium, hard), namesTypeAttacks, user.getId());
        List<AttackDto> attackToShow = new ArrayList<>();

        for(AttackDto attackDto : attacksDtoSearched){
            Attack attack = attackService.getOneById(attackDto.getId());
            List<UserAttack> userAttacks = attack.getUserAttacks();

            for(UserAttack userAttack: userAttacks){
                if(userAttack.getUserAttackId().getUserId() == user.getId() && userAttack.isSaved()){
                    attackToShow.add(attackDto);
                    break;
                }
            }

        }

        model.addAttribute("user", user.getUserProfile());

        model.addAttribute("attacks", attackToShow);

        return "saved-attacks";
    }
}
