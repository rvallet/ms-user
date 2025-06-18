package com.vallet.ms_user.repository;

import com.vallet.ms_user.enums.CreatorType;
import com.vallet.ms_user.model.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {

    Optional<Utilisateur> findByLogin(String login);
    List<Utilisateur> findAllByLoginIn(List<String> logins);
    List<Utilisateur> findAllByIdIn(List<String> ids);

    void deleteByLogin(String login);
}
