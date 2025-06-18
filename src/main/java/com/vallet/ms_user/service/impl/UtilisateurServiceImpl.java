package com.vallet.ms_user.service.impl;

import com.vallet.ms_user.dto.UserDto;
import com.vallet.ms_user.model.CommonData;
import com.vallet.ms_user.model.Utilisateur;
import com.vallet.ms_user.repository.UtilisateurRepository;
import com.vallet.ms_user.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur createUser(UserDto utilisateur) {
        CommonData commonData = new CommonData();
        commonData.setCreatedAt(LocalDateTime.now());
        commonData.setCreatedBy("system");
        commonData.setUpdatedAt(LocalDateTime.now());
        commonData.setUpdatedBy("system");

        Utilisateur newUser = new Utilisateur();
        newUser.setLogin(utilisateur.getLogin());
        newUser.setNom(utilisateur.getNom());
        newUser.setPrenom(utilisateur.getPrenom());
        newUser.setAdresses(utilisateur.getAdresses());
        newUser.setCommonData(commonData);
        // Hash the password before saving
        newUser.setMotDePasse(null);
        return utilisateurRepository.save(newUser);
    }

    @Override
    public Utilisateur updateUser(UserDto utilisateur) {
        Utilisateur existingUser = utilisateurRepository.findByLogin(utilisateur.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setNom(utilisateur.getNom());
        existingUser.setPrenom(utilisateur.getPrenom());
        existingUser.setAdresses(utilisateur.getAdresses());
        CommonData commonData = existingUser.getCommonData();
        if (commonData == null) {
            commonData = new CommonData();
        }
        commonData.setUpdatedAt(LocalDateTime.now());
        commonData.setUpdatedBy("system");
        existingUser.setCommonData(commonData);
        // Hash the password before saving
        existingUser.setMotDePasse(null);
        return utilisateurRepository.save(existingUser);
    }

    @Override
    public Optional<Utilisateur> getUserById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Utilisateur> getUserByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }

    @Override
    public List<Utilisateur> getUsersByLoginList(List<String> logins) {
        return List.of();
    }

    @Override
    public List<Utilisateur> getUserByIdList(List<String> ids) {
        return List.of();
    }

    @Override
    public void deleteUserById(String id) {

    }

    @Override
    public void deleteUserByLogin(String login) {

    }
}
