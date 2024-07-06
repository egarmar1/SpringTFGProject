package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.ContainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContainerInfoRepository extends JpaRepository<ContainerInfo,Integer> {

    Optional<ContainerInfo> findByContainerId(String containerId);
}
