package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.TypeAttack;
import com.hackWeb.hackWeb.repository.TypeAttackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeAttackService {

    private final TypeAttackRepository typeAttackRepository;


    public TypeAttackService(TypeAttackRepository typeAttackRepository) {
        this.typeAttackRepository = typeAttackRepository;
    }


    public List<TypeAttack> getAll() {
        return typeAttackRepository.findAll();
    }

    public List<String> getAllNames() {
        List<TypeAttack> typeAttacks = getAll();

        return typeAttacks.stream()
                .map(TypeAttack::getName)
                .collect(Collectors.toList());


    }
}
