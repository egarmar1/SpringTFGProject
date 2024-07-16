package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.entity.ContainerInfo;
import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.repository.ContainerInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerInfoService {
    private final ContainerInfoRepository containerInfoRepository;

    public ContainerInfoService(ContainerInfoRepository containerInfoRepository) {
        this.containerInfoRepository = containerInfoRepository;
    }


    public List<ContainerInfo> getContainersByUserAndImage(int userId, String dockerImageName) {
        return containerInfoRepository.findByUserIdAndImageName(userId, dockerImageName);
    }

    public void createContainerInDB(String containerId, int websockifyHostPort, String password, int containerHostPort, String networkId, User currentUser, Attack attack) {
        ContainerInfo containerInfo = new ContainerInfo();
        containerInfo.setContainerId(containerId);
        containerInfo.setWebSockifyPort(websockifyHostPort);
        containerInfo.setVncPassword(password);
        containerInfo.setContainerPort(containerHostPort);
        containerInfo.setNetworkId(networkId);
        containerInfo.setUser(currentUser);
        containerInfo.setAttack(attack);

        containerInfoRepository.save(containerInfo);
    }

    public void createContainerInDB(String containerId, String networkId, User currentUser, Attack attack) {
        ContainerInfo containerInfo = new ContainerInfo();
        containerInfo.setContainerId(containerId);
        containerInfo.setNetworkId(networkId);
        containerInfo.setUser(currentUser);
        containerInfo.setAttack(attack);

        containerInfoRepository.save(containerInfo);
    }
}
