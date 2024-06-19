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
import java.util.stream.Collectors;

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


        User user = userService.getCurrentUser();

        String attackBar = "";

        List<String> difficulties = Arrays.asList("Easy", "Medium", "Hard");
        List<String> namesTypeAttacks = typeAttackService.getAllNames();

        List<AttackDto> attacksDtoSearched = attackService.searchDto(attackBar, difficulties, namesTypeAttacks, user.getId());
//        List<AttackDto> attackToShow = new ArrayList<>();
//
//
//        for(AttackDto attackDto : attacksDtoSearched){
//            Attack attack = attackService.getOneById(attackDto.getId());
//            List<UserAttack> userAttacks = attack.getUserAttacks();
//
//            for(UserAttack userAttack: userAttacks){
//                if(userAttack.getUserAttackId().getUserId() == user.getId() && userAttack.isSaved()){
//                    attackToShow.add(attackDto);
//                    break;
//                }
//            }
//
//        }

        List<AttackDto> attackToShow = attacksDtoSearched.stream()
                        .filter(attackDto -> {
                            Attack attack = attackService.getOneById(attackDto.getId());
                            return attack.getUserAttacks().stream()
                                    .anyMatch( userAttack -> userAttack.getUserAttackId().getUserId() == user.getId() && userAttack.isSaved());
                        })
                                .collect(Collectors.toList());

        model.addAttribute("user", user.getUserProfile());

        model.addAttribute("attacks", attackToShow);

        return "saved-attacks";
    }
}
