package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.UserType;
import com.hackWeb.hackWeb.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAll(){
        return userTypeRepository.findAll();
    }


    public UserType findByUserTypeName(String studentTypeName) {
        return userTypeRepository.findByName(studentTypeName);
    }
}
