package com.vallet.ms_user.controller;

import com.vallet.ms_user.ApiRegistration;
import com.vallet.ms_user.dto.ChangePasswordRequest;
import com.vallet.ms_user.dto.LoginRequest;
import com.vallet.ms_user.dto.UserDto;
import com.vallet.ms_user.model.Utilisateur;
import com.vallet.ms_user.service.impl.UtilisateurServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.vallet.ms_user.exception.ExceptionCause.getStatusFromMessage;

@RestController
@RequestMapping(ApiRegistration.REST_PREFIX)
public class UtilisateurController {

    @Autowired
    private UtilisateurServiceImpl utilisateurService;

    @PostMapping(ApiRegistration.CREATE)
    public ResponseEntity<Utilisateur> createUser(@RequestBody @Valid UserDto utilisateur) {
        Utilisateur created = utilisateurService.upsertUser(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(ApiRegistration.UPDATE)
    public ResponseEntity<Utilisateur> updateUser(@RequestBody @Valid UserDto utilisateur) {
        Utilisateur updated = utilisateurService.upsertUser(utilisateur);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @GetMapping(ApiRegistration.ID + "/{id}")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable String id) {
        return utilisateurService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(ApiRegistration.LOGIN + "/{login}")
    public ResponseEntity<Utilisateur> getUtilisateurByLogin(@PathVariable String login) {
        return utilisateurService.getUserByLogin(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(ApiRegistration.LOGIN)
    public ResponseEntity<UserDto> login(@RequestBody @Valid LoginRequest loginRequest) {
        return utilisateurService.userAuthenticate(loginRequest.getLogin(), loginRequest.getMotDePasse())
                .map(user -> ResponseEntity.ok(
                        new UserDto(
                        user.getLogin(),
                        null,
                        user.getNom(),
                        user.getPrenom(),
                        user.getAdresses())
                        )
                    )
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping(ApiRegistration.LOGIN + "/list")
    public List<UserDto> getUsersByLoginList(@RequestBody List<String> logins) {
        List<Utilisateur> utilisateurs = utilisateurService.getUsersByLoginList(logins);
        return utilisateurs.stream()
                .map(user -> new UserDto(
                        user.getLogin(),
                        null,
                        user.getNom(),
                        user.getPrenom(),
                        user.getAdresses()))
                .toList();
    }

    @GetMapping(ApiRegistration.ID + "/list")
    public List<UserDto> getUsersByIdList(@RequestBody List<String> ids) {
        List<Utilisateur> utilisateurs = utilisateurService.getUsersByIdList(ids);
        return utilisateurs.stream()
                .map(user -> new UserDto(
                        user.getLogin(),
                        null,
                        user.getNom(),
                        user.getPrenom(),
                        user.getAdresses()))
                .toList();
    }

    @DeleteMapping(ApiRegistration.ID + "/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable String id) {
        utilisateurService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(ApiRegistration.LOGIN + "/{login}")
    public ResponseEntity<Void> deleteUtilisateurByLogin(@PathVariable String login) {
        utilisateurService.deleteUserByLogin(login);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(ApiRegistration.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            utilisateurService.changePassword(request.getLogin(), request.getNewPassword(), request.getPasswordRegex());
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            HttpStatus status = getStatusFromMessage(e.getMessage());
            return ResponseEntity.status(status).body(e.getMessage());
        }
    }

}
