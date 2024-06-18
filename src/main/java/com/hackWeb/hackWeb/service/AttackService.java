package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.*;
import com.hackWeb.hackWeb.repository.AttackRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttackService {

    private final AttackRepository attackRepository;

    public AttackService(AttackRepository attackRepository) {
        this.attackRepository = attackRepository;
    }

    public List<Attack> getAll() {
        return attackRepository.findAll();
    }
    public Attack getOneById(int id){
        return attackRepository.findById(id).orElseThrow(() -> new RuntimeException("Attack not found"));
    }

    public List<Attack> search(String attackTitle, List<String> difficulties, List<String> typeAttacks) {

        System.out.println("These are the difficulties: " + difficulties + ". And this are the typeAttacks " + typeAttacks + ". And this is the attack " + attackTitle);
        return attackRepository.search(difficulties, typeAttacks, attackTitle);

    }

    public List<AttackDto> searchDto(String attackTitle, List<String> difficulties, List<String> typeAttacks, int userId) {

        List<IAttack> iAttacks = attackRepository.searchDto(difficulties,typeAttacks,attackTitle, userId);

        System.out.println("The iAttacks are: " + iAttacks);
        return  iAttacks.stream()
                .map(this::convertToAttackDto)
                .collect(Collectors.toList());
    }

    private AttackDto convertToAttackDto(IAttack ia){
        TypeAttack typeAttack = new TypeAttack(ia.getTypeAttackId(),ia.getTypeAttackName());
        boolean isSaved = ia.getIsSaved() != null && ia.getIsSaved() == 1;
        boolean isCompleted = ia.getIsCompleted() != null && ia.getIsCompleted() == 1;

        return new AttackDto(ia.getTotalStudentsCompleted(), ia.getId(), ia.getTitle(), ia.getDifficulty(), typeAttack, isSaved, isCompleted,ia.getDescription(), ia.getPostedDate(),ia.getPreVideoFile(), ia.getSolutionVideoFile());

    }

    public AttackDto getOneByAttackIdAndUserId(int attackId, int userId) {

        IAttack iAttack = attackRepository.getOneDtoById(attackId, userId);

        System.out.println("Vale este es el postedDate que estamos cogiendo: " + iAttack.getPostedDate());
        return convertToAttackDto(iAttack);
    }
}
