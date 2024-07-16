package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.ContainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContainerInfoRepository extends JpaRepository<ContainerInfo,Integer> {

    Optional<ContainerInfo> findByContainerId(String containerId);

    @Query(value = "SELECT c.* FROM container_info c " +
            "INNER JOIN attack a ON a.id = c.attack_id " +
            "WHERE a.docker_image_name LIKE %:dockerImageName% " +
            "AND c.user_id = :userId;",nativeQuery = true)
    List<ContainerInfo> findByUserIdAndImageName(@Param("userId") int userId,
                                                 @Param("dockerImageName") String dockerImageName);

}
