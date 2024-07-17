package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.entity.ContainerInfo;
import com.hackWeb.hackWeb.service.AttackService;
import com.hackWeb.hackWeb.service.ContainerInfoService;
import com.hackWeb.hackWeb.service.DockerService;
import com.hackWeb.hackWeb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/docker")
public class DockerController {


    private final DockerService dockerService;
    private final ContainerInfoService containerInfoService;
    private final AttackService attackService;
    private final UserService userService;

    public DockerController(DockerService dockerService, ContainerInfoService containerInfoService, AttackService attackService, UserService userService) {
        this.dockerService = dockerService;
        this.containerInfoService = containerInfoService;
        this.attackService = attackService;
        this.userService = userService;
    }


    @GetMapping("/connect")
    public RedirectView connect(@RequestParam("dockerImageName") String imageName) {
        List<ContainerInfo> containersByUserAndImage = containerInfoService.getContainersByUserAndImage(userService.getCurrentUser().getId(), imageName);
        ContainerInfo containerWithVncPort = containersByUserAndImage.stream()
                .filter(container -> container.getWebSockifyPort() != null).findFirst().orElse(null);

        if (containerWithVncPort != null) {
            return new RedirectView("http://localhost:" + containerWithVncPort.getWebSockifyPort() + "/vnc.html?password=" + containerWithVncPort.getVncPassword());
        }
        String vncPassword = generatePassword();

        Attack attack = attackService.getOneByDockerImageName(imageName);
        String containerId = dockerService.createContainers(imageName, vncPassword, attack.getInitSqlPathName(), attack.getDatabaseName());

        Map<String, String> containerInfo = dockerService.getContainerInfo(containerId);
        String vncPort = containerInfo.get("vncPort");

        return new RedirectView("http://localhost:" + vncPort + "/vnc.html?password=" + vncPassword);

    }

    @GetMapping("/reset")
    public String reset(@RequestParam("dockerImageName") String imageName) {
        List<ContainerInfo> containersByUserAndImage = containerInfoService.getContainersByUserAndImage(userService.getCurrentUser().getId(), imageName);
        ContainerInfo containerWithVncPort = containersByUserAndImage.stream()
                .filter(container -> container.getWebSockifyPort() != null).findFirst().orElse(null);

        Attack attack = attackService.getOneByDockerImageName(imageName);
        if (containerWithVncPort == null) {

            return "redirect:/attack-details/" + attack.getId() + "?noLabActive=false";
        }

        containersByUserAndImage.forEach(container -> {
            dockerService.removeContainerAndItsVnc(container.getContainerId());
        });
        dockerService.removeNetwork(containersByUserAndImage.getFirst().getNetworkId());

        String vncPassword = generatePassword();
        String containerId = dockerService.createContainers(imageName, vncPassword, attack.getInitSqlPathName(), attack.getDatabaseName());

        Map<String, String> containerInfo = dockerService.getContainerInfo(containerId);
        String vncPort = containerInfo.get("vncPort");

        return "redirect:/attack-details/" + attack.getId() + "?justReseted=true";
    }

    private String generatePassword() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[8];
        secureRandom.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

}