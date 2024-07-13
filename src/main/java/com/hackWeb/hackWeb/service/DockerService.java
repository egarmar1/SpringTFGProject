package com.hackWeb.hackWeb.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.hackWeb.hackWeb.entity.ContainerInfo;
import com.hackWeb.hackWeb.repository.AttackRepository;
import com.hackWeb.hackWeb.repository.ContainerInfoRepository;
import com.github.dockerjava.api.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class DockerService {

    private final DockerClient dockerClient;
    private final ContainerInfoRepository containerInfoRepository;
    private final UserService userService;
    private final AttackRepository attackRepository;

    private Process websockifyProcess;
    private static final String INIT_SQL_PATH = "G:/Mi unidad/hackWeb/docker/sqli1Init.sql";

    public DockerService(DockerClient dockerClient, ContainerInfoRepository containerInfoRepository, UserService userService, AttackRepository attackRepository) {
        this.dockerClient = dockerClient;
        this.containerInfoRepository = containerInfoRepository;
        this.userService = userService;
        this.attackRepository = attackRepository;
    }


    //    @Transactional
    public String createContainers(String imageName, String vncPassword) {

        String networkName = createUniqueNetwork();
        String uniqueMySQLContainerName = "mysql-" + UUID.randomUUID();

        int containerHostPort = findFreePort();
        int websockifyHostPort = findFreePort();


        CreateContainerResponse mysqlContainer = dockerClient.createContainerCmd("mysql:latest")
                .withName(uniqueMySQLContainerName)
                .withEnv("MYSQL_ROOT_PASSWORD=Kike2016+")
                .withHostConfig(HostConfig.newHostConfig()
                        .withMounts(Collections.singletonList(new Mount().withType(MountType.BIND).withSource(INIT_SQL_PATH).withTarget("/docker-entrypoint-initdb.d/sqli1Init.sql")))
                        .withNetworkMode(networkName))
                .exec();

        dockerClient.startContainerCmd(mysqlContainer.getId()).exec();
        try {
            Thread.sleep(5000);

            // Crear el contenedor de la aplicación Spring Boot
            String springDatasourceUrl = "SPRING_DATASOURCE_URL=jdbc:mysql://" + uniqueMySQLContainerName + ":3306/sqliWeb";
            CreateContainerResponse appContainer = dockerClient.createContainerCmd(imageName)
                    .withHostConfig(HostConfig.newHostConfig()
                            .withPortBindings(new PortBinding(Ports.Binding.bindPort(containerHostPort), ExposedPort.tcp(5901)))
                            .withNetworkMode(networkName))
                    .withEnv("VNC_PASSWORD=" + vncPassword,
                            springDatasourceUrl)
                    .exec();

            dockerClient.startContainerCmd(appContainer.getId()).exec();

            startWebSockify(websockifyHostPort, containerHostPort);

            ContainerInfo containerInfo = new ContainerInfo();
            containerInfo.setContainerId(appContainer.getId());
            containerInfo.setWebSockifyPort(websockifyHostPort);
            containerInfo.setContainerPort(containerHostPort);
            containerInfo.setUser(userService.getCurrentUser());
            containerInfo.setAttack(attackRepository.findByDockerImageName(imageName));
            containerInfoRepository.save(containerInfo);


            Thread.sleep(1000);

            return appContainer.getId();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }

    }

    private String createUniqueNetwork() {
        String networkName;
        do {
            networkName = generateRandomString();
        } while (networkExists(networkName));

        dockerClient.createNetworkCmd().withName(networkName).exec();
        return networkName;
    }

    private boolean networkExists(String networkName) {
        return dockerClient.listNetworksCmd().exec().stream()
                .anyMatch(network -> network.getName().equals(networkName));
    }

    private String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(8);
    }


    private void startWebSockify(int websockifyHostPort, int containerHostPort) {
        new Thread(() -> {
            try {
                File workingDir = new File("src/main/resources/static/noVNC");

                ProcessBuilder processBuilder = new ProcessBuilder(
                        "websockify", "--web", "./", String.valueOf(websockifyHostPort), "localhost:" + containerHostPort);
                processBuilder.redirectErrorStream(true);
                processBuilder.directory(workingDir);
                websockifyProcess = processBuilder.start();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start();

    }

    public void stopWebSockify(String containerId) {
        ContainerInfo containerInfo = containerInfoRepository.findByContainerId(containerId).orElseThrow(() -> new RuntimeException("Container not found"));

        int webSockifyPort = containerInfo.getWebSockifyPort();

        try {
            // Ejecutar el comando netstat para encontrar el PID que usa el puerto
            ProcessBuilder netstatProcessBuilder = new ProcessBuilder("netstat", "-ano");
            Process netstatProcess = netstatProcessBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(netstatProcess.getInputStream()));
            String line;
            String pid = null;

            while ((line = reader.readLine()) != null) {
                if (line.contains(":" + webSockifyPort)) {
                    String[] tokens = line.trim().split("\\s+");
                    pid = tokens[tokens.length - 1]; // El PID está al final de la línea
                    break;
                }
            }

            if (pid != null) {
                // Ejecutar el comando taskkill para terminar el proceso
                ProcessBuilder taskkillProcessBuilder = new ProcessBuilder("taskkill", "/PID", pid, "/F");
                taskkillProcessBuilder.start();


            } else {
                System.out.println("No process found using port " + webSockifyPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error stopping WebSockify process", e);
        }
    }

    @Transactional
    public void stopAndRemoveContainer(String containerId) {

        executeDockerCommand(() -> dockerClient.stopContainerCmd(containerId).exec(), containerId);
        executeDockerCommand(() -> dockerClient.removeContainerCmd(containerId).exec(), containerId);
        removeContainer(containerId);
    }

    private void executeDockerCommand(Runnable command, String containerId) {
        try {
            command.run();
        } catch (NotFoundException e) {
            System.out.println("Contenedor no encontrado: " + containerId);
        }
    }

    public void removeContainer(String containerId) {
        ContainerInfo containerInfo = containerInfoRepository.findByContainerId(containerId)
                .orElseThrow(() -> new RuntimeException("Container not found"));

        containerInfoRepository.delete(containerInfo);
    }

    public Map<String, String> getContainerInfo(String containerId) {
        var container = dockerClient.inspectContainerCmd(containerId).exec();

        ContainerInfo containerInfo = containerInfoRepository.findByContainerId(containerId).orElseThrow(() -> new RuntimeException("No container with id" + containerId));


        Map<String, String> info = new HashMap<>();

        info.put("containerId", container.getId());
        info.put("status", container.getState().getStatus());
        info.put("vncPort", containerInfo.getWebSockifyPort().toString());

        return info;
    }

    public List<ContainerInfo> getExpiredContainers() {
        List<ContainerInfo> containersInfo = containerInfoRepository.findAll();


        return containersInfo.stream()
                .filter(container -> container.getExpiryDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

    }


    private int findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("No free port found", e);
        }
    }
}
