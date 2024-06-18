package com.hackWeb.hackWeb.repository;


import com.hackWeb.hackWeb.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType,Integer> {

    UserType findByName(String userTypeName);
}
