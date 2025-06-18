package com.vallet.ms_user.service;

import com.vallet.ms_user.dto.UserDto;
import com.vallet.ms_user.model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    Utilisateur createUser(UserDto utilisateur);
    Utilisateur upsertUser(UserDto utilisateur);
    Optional<Utilisateur> getUserById(String id);
    Optional<Utilisateur> getUserByLogin(String login);
    List<Utilisateur> getUsersByLoginList(List<String> logins);
    List<Utilisateur> getUsersByIdList(List<String> ids);
    void deleteUserById(String id);
    void deleteUserByLogin(String login);
    Optional<Utilisateur> userAuthenticate(String login, String motDePasse);

}
