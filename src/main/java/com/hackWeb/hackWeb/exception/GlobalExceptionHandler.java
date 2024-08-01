package com.hackWeb.hackWeb.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

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

}
