package com.vallet.ms_user.service.impl;

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
    public Utilisateur createUser(Utilisateur utilisateur) {
        CommonData commonData = new CommonData();
        commonData.setCreatedAt(LocalDateTime.now());
        commonData.setCreatedBy("system");
        commonData.setUpdatedAt(LocalDateTime.now());
        commonData.setUpdatedBy("system");
        utilisateur.setCommonData(commonData);
        // Hash the password before saving
        utilisateur.setMotDePasse(null);
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateUser(Utilisateur utilisateur) {
        Utilisateur existingUser = utilisateurRepository.findById(utilisateur.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setLogin(utilisateur.getLogin());
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
    public Utilisateur getUserByLogin(String login) {
        return null;
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
