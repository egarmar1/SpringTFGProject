package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.entity.AttackDto;
import com.hackWeb.hackWeb.entity.TypeAttack;
import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.service.AttackService;
import com.hackWeb.hackWeb.service.TypeAttackService;
import com.hackWeb.hackWeb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    private final UserService userService;
    private final AttackService attackService;

    private final TypeAttackService typeAttackService;

    public DashboardController(UserService userService, AttackService attackService, TypeAttackService typeAttackService) {
        this.userService = userService;
        this.attackService = attackService;
        this.typeAttackService = typeAttackService;
    }

    @GetMapping("/dashboard/")
    public String dashboard(Model model,
                            @RequestParam(value = "easy", required = false) String easy,
                            @RequestParam(value = "medium", required = false) String medium,
                            @RequestParam(value = "hard", required = false) String hard,
                            @RequestParam(value = "selectedAttacks", required = false) List<String> namesTypeAttacks,
                            @RequestParam(value = "attackBar", required = false) String attackBar
    ) {

        if (!(namesTypeAttacks == null)) {
            System.out.println("SelectedAttacks are: " + namesTypeAttacks);

        }

        User user = userService.getCurrentUser();
        List<Attack> attacksSearched;
        List<AttackDto> attacksDtoSearched = new ArrayList<>();
        boolean difficulty = true;
        boolean type = true;

        if (easy == null && medium == null && hard == null) {
            easy = "Easy";
            medium = "Medium";
            hard = "Hard";
            difficulty = false;
        }

        if ((namesTypeAttacks == null)) {
            namesTypeAttacks = typeAttackService.getAllNames();
            type = false;
        }

        if (attackBar == null){
            attackBar = "";
        }


//        if(!difficulty && !type && !StringUtils.hasText(attackBar)){
//            attacksSearched = attackService.getAll();
//        }else{
        //attacksSearched = attackService.search(attackBar,Arrays.asList(easy,medium,hard), namesTypeAttacks);
        attacksDtoSearched = attackService.searchDto(attackBar, Arrays.asList(easy, medium, hard), namesTypeAttacks, user.getId());

//        }

        System.out.println("The attacksDto searched are: " + attacksDtoSearched);


        model.addAttribute("user", user.getUserProfile());


        List<TypeAttack> typeAttacks = typeAttackService.getAll();


        model.addAttribute("attacks", attacksDtoSearched);
        model.addAttribute("typeAttacks", typeAttacks);

        return "dashboard";
    }
}
