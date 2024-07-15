package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.service.AttackService;
import com.hackWeb.hackWeb.service.DockerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@Controller
@RequestMapping("/docker")
public class DockerController {


    private final DockerService dockerService;
    private final AttackService attackService;

    public DockerController(DockerService dockerService, AttackService attackService) {
        this.dockerService = dockerService;
        this.attackService = attackService;
    }


    @GetMapping("/connect")
    public RedirectView connect(@RequestParam("dockerImageName") String imageName ){
        String vncPassword = generatePassword();

        Attack attack = attackService.getOneByDockerImageName(imageName);
        String containerId = dockerService.createContainers(imageName, vncPassword,attack.getInitSqlPathName(), attack.getDatabaseName());

        Map<String, String> containerInfo = dockerService.getContainerInfo(containerId);
        String vncPort = containerInfo.get("vncPort");

        return new RedirectView("http://localhost:" + vncPort + "/vnc.html?password=" + vncPassword);

    }

    private String generatePassword() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[8];
        secureRandom.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    @GetMapping("/disconnect")
    public void disconnect(@RequestParam("containerId") String containerId){
//        dockerService.stopAndRemoveContainer(containerId);
        dockerService.stopWebSockify(containerId);
        dockerService.removeNetwork(containerId);
    }
}