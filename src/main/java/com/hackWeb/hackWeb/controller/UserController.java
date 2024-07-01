package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.entity.UserType;
import com.hackWeb.hackWeb.service.UserService;
import com.hackWeb.hackWeb.service.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class UserController {

    private final UserTypeService userTypeService;
    private final UserService userService;
    public UserController(UserTypeService userTypeService, UserService userService) {
        this.userTypeService = userTypeService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null){
            model.addAttribute("error", model.addAttribute(error));
        }
        System.out.println("Vamos con el error" + error);

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        List<UserType> userTypes = userTypeService.getAll();
        model.addAttribute("getAllTypes");

        System.out.println("El primer userType es: " + userTypes.getFirst());
        return "register";
    }

    @PostMapping("/register/new")
    public String registerNew(@Valid User user, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            return "register";
        }
//        UserType userTypeStudent = userTypeService.findByUserTypeName("Student");
//        user.setUserType(userTypeStudent);

        try {
            userService.addNewStudent(user);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("emailError", "El email ya está registrado");
            return "register";
        }


        return "redirect:/dashboard/";
    }
}
