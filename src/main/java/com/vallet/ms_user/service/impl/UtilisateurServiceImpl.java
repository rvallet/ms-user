package com.vallet.ms_user.service.impl;

import com.vallet.ms_user.dto.UserDto;
import com.vallet.ms_user.enums.CreatorType;
import com.vallet.ms_user.model.CommonData;
import com.vallet.ms_user.model.Utilisateur;
import com.vallet.ms_user.repository.UtilisateurRepository;
import com.vallet.ms_user.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordServiceImpl passwordService;

    /**
     * Creates a new user based on the provided UserDto.
     * The password is hashed using the existing password service.
     *
     * @param utilisateur the UserDto containing user details
     * @return the created Utilisateur entity
     */
    @Override
    public Utilisateur createUser(UserDto utilisateur) {
        CommonData commonData = new CommonData();
        Utilisateur newUser = new Utilisateur();
        newUser.setLogin(utilisateur.getLogin());
        newUser.setNom(utilisateur.getNom());
        newUser.setPrenom(utilisateur.getPrenom());
        newUser.setAdresses(utilisateur.getAdresses());
        newUser.setCommonData(commonData);
        // Hash the password with the existing password service
        newUser.setMotDePasse(passwordService.hashPassword(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(newUser);
    }

    /**
     * Updates an existing user or creates a new one if it does not exist.
     * The password is hashed using the existing password service.
     *
     * @param utilisateur the UserDto containing user details
     * @return the updated or created Utilisateur entity
     */
    @Override
    public Utilisateur upsertUser(UserDto utilisateur) {
        Optional<Utilisateur> existingUserOpt = utilisateurRepository.findByLogin(utilisateur.getLogin());
        return existingUserOpt.map(existingUser -> updateUser(utilisateur, existingUser)).orElseGet(() -> createUser(utilisateur));
    }

    /**
     * Updates the existing user with the details from the UserDto.
     * The password is hashed using the existing password service if it has changed.
     *
     * @param utilisateur the UserDto containing user details
     * @param existingUser the existing Utilisateur entity to update
     * @return the updated Utilisateur entity
     */
    private Utilisateur updateUser(UserDto utilisateur, Utilisateur existingUser) {
        existingUser.setNom(utilisateur.getNom());
        existingUser.setPrenom(utilisateur.getPrenom());
        existingUser.setAdresses(utilisateur.getAdresses());
        CommonData commonData = existingUser.getCommonData();
        if (commonData == null) {
            commonData = new CommonData();
        }
        commonData.setUpdatedAt(LocalDateTime.now());
        commonData.setUpdatedBy(CreatorType.SYSTEM);
        existingUser.setCommonData(commonData);

        // Hash the password with the existing password service
        if (StringUtils.hasLength(utilisateur.getMotDePasse()) && !passwordService.matches(utilisateur.getMotDePasse(), existingUser.getMotDePasse())) {
            existingUser.setMotDePasse(passwordService.hashPassword(utilisateur.getMotDePasse()));
        }

        return utilisateurRepository.save(existingUser);
    }

    /**
     * Retrieves a user by their ID.
     * @param id - the ID of the user to retrieve
     * @return an Optional containing the user if found, or empty if not found
     */
    @Override
    public Optional<Utilisateur> getUserById(String id) {
        return utilisateurRepository.findById(id);
    }

    /**
     * Retrieves a user by their login.
     * @param login -
     * @return an Optional containing the user if found, or empty if not found
     */
    @Override
    public Optional<Utilisateur> getUserByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }

    /**
     * Retrieves a list of users by their logins.
     * @param logins - a list of logins to search for
     * @return a list of Utilisateur entities matching the provided logins
     */
    @Override
    public List<Utilisateur> getUsersByLoginList(List<String> logins) {
        return utilisateurRepository.findAllByLoginIn(logins);
    }

    /**
     * Retrieves a list of users by their IDs.
     * @param ids - a list of IDs to search for
     * @return a list of Utilisateur entities matching the provided IDs
     */
    @Override
    public List<Utilisateur> getUsersByIdList(List<String> ids) {
        return utilisateurRepository.findAllByIdIn(ids);
    }

    /**
     * Deletes a user by their ID.
     * @param id - the ID of the user to delete
     */
    @Override
    public void deleteUserById(String id) {
        utilisateurRepository.deleteById(id);
    }

    /**
     * Deletes a user by their login.
     * @param login - the login of the user to delete
     */
    @Override
    public void deleteUserByLogin(String login) {
        utilisateurRepository.deleteByLogin(login);
    }

    /**
     * Authenticates a user by their login and password.
     * Throws an exception if the authentication fails.
     *
     * @param login - the login of the user
     * @param motDePasse - the password of the user
     */
    @Override
    public Optional<Utilisateur> userAuthenticate(String login, String motDePasse) {
        Optional<Utilisateur> userOpt = utilisateurRepository.findByLogin(login);
        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();
            if (passwordService.matches(motDePasse, user.getMotDePasse())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}
