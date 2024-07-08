package com.hackWeb.hackWeb.controller;

import com.github.dockerjava.api.DockerClient;
import com.hackWeb.hackWeb.service.DockerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("/docker")
public class DockerController {


    private final DockerService dockerService;

    public DockerController(DockerService dockerService) {
        this.dockerService = dockerService;
    }


    @GetMapping("/connect")
    public RedirectView connect(@RequestParam("dockerImageName") String imageName ){
        String containerId = dockerService.createContainer(imageName);

        Map<String, String> containerInfo = dockerService.getContainerInfo(containerId);
        String vncPort = containerInfo.get("vncPort");

        return new RedirectView("http://localhost:" + vncPort + "/vnc.html");

    }


    @GetMapping("/disconnect")
    public void disconnect(@RequestParam("containerId") String containerId){
        dockerService.stopAndRemoveContainer(containerId);
        dockerService.stopWebSockify(containerId);
    }
}