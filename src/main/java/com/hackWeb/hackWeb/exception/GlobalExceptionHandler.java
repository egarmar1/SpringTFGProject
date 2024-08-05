package com.hackWeb.hackWeb.exception;

import com.hackWeb.hackWeb.service.TypeAttackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final TypeAttackService typeAttackService;

    public GlobalExceptionHandler(TypeAttackService typeAttackService) {
        this.typeAttackService = typeAttackService;
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public String handleNotFoundException(UsernameNotFoundException e, RedirectAttributes redirectAttributes){

        System.out.println("Hola pasamos por aquí");
        redirectAttributes.addFlashAttribute("error" , e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(DockerConnectionException.class)
    public ResponseEntity<Object> handleDockerConnectionException(DockerConnectionException ex, WebRequest request){
        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler(AttackNotFoundException.class)
    public ResponseEntity<Object> attackNotFoundException(AttackNotFoundException ex, WebRequest request){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageAttackExistsOnCreationException.class)
    public String handleImageAttackExistsOnCreationException(ImageAttackExistsOnCreationException ex, Model model){
        logger.error("Handling ImageAttackExistsOnCreationException: {}", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("typeAttacks", typeAttackService.getAll());
        return "add-attack";
    }

    @ExceptionHandler(ImageAttackExistsOnUpdateException.class)
    public String handleImageAttackExistsOnUpdateException(ImageAttackExistsOnCreationException ex, Model model){
        logger.error("Handling ImageAttackExistsOnUpdateException: {}", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("typeAttacks", typeAttackService.getAll());

        return "edit-attack";
    }
    @ExceptionHandler(PhisicallySaveVideoException.class)
    public String handlePhisicallySaveVideoException(ImageAttackExistsOnCreationException ex, Model model){
        logger.error("Handling PhisicallySaveVideoException: {}", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "add-attack";
    }

    @ExceptionHandler(GeneralExceptionWithContext.class)
    public String handleGeneralExceptionWithContext(GeneralExceptionWithContext ex, Model model) {
        logger.error("Handling GeneralExceptionWithContext: {}", ex.getMessage());
        model.addAttribute("error", ex.getMessage());

        return ex.getContext();
    }

//    @ExceptionHandler(Exception.class)
//    public String handleGeneralException(Exception ex, Model model) {
//
//        logger.error("Handling General Exception: ", ex);
//        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
//        return "error";
//    }

}
