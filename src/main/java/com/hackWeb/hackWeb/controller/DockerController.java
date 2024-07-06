package com.hackWeb.hackWeb.controller;

import com.github.dockerjava.api.DockerClient;
import com.hackWeb.hackWeb.service.DockerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/docker")
public class DockerController {


    private final DockerService dockerService;

    public DockerController(DockerService dockerService) {
        this.dockerService = dockerService;
    }


    @PostMapping("/connect")
    public String  connect(@RequestParam String imageName ){
        String containerId = dockerService.createContainer(imageName);

        Map<String, String> containerInfo = dockerService.getContainerInfo(containerId);
        System.out.println(containerInfo);
        return containerInfo.toString();

    }


    @PostMapping("/disconnect")
    public void disconnect(@RequestParam String containerId){
        dockerService.stopAndRemoveContainer(containerId);
        dockerService.stopWebSockify(containerId);
    }
}
