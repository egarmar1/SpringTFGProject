package com.hackWeb.hackWeb.config;

import com.hackWeb.hackWeb.entity.ContainerInfo;
import com.hackWeb.hackWeb.service.DockerService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    private final DockerService dockerService;

    public SchedulerConfig(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @Scheduled(fixedRate = 3600000) // cada hora se limpiaran los contenedores
    public void checkAndDisconnectExpiredContainers() {
        List<ContainerInfo> expiredContainers = dockerService.getExpiredContainers();

        Map<String, List<ContainerInfo>> expiredContainersByNetwork = expiredContainers.stream()
                .collect(Collectors.groupingBy(ContainerInfo::getNetworkId));

        System.out.println("Expired Containers are: " + expiredContainers);
        System.out.println("Container by network: " + expiredContainersByNetwork);

        expiredContainersByNetwork
                .forEach((networkId, containersList) -> {
                    containersList.forEach(container -> {
                        if (container.getWebSockifyPort() != null) {
                            dockerService.stopWebSockify(container.getContainerId());
                        }
                        dockerService.stopAndRemoveContainer(container.getContainerId());
                    });

                    dockerService.removeNetwork(networkId);
                });
    }
}
