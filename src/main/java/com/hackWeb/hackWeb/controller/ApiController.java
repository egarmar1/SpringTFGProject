package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.service.AttackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final AttackService attackService;

    public ApiController(AttackService attackService) {
        this.attackService = attackService;
    }


    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello you");
    }

    @GetMapping("/attack/{id}")
    public Attack getAttackById(@PathVariable Integer id){
        return attackService.getOneById(id);

    }
}

