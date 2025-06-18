package com.vallet.ms_user.controller;

import com.vallet.ms_user.ApiRegistration;
import com.vallet.ms_user.dto.LoginRequest;
import com.vallet.ms_user.dto.UserDto;
import com.vallet.ms_user.model.Utilisateur;
import com.vallet.ms_user.service.impl.UtilisateurServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiRegistration.REST_PREFIX)
public class UtilisateurController {

    @Autowired
    private UtilisateurServiceImpl utilisateurService;

    @PostMapping("/create")
    public ResponseEntity<Utilisateur> createUser(@RequestBody @Valid UserDto utilisateur) {
        Utilisateur created = utilisateurService.createUser(utilisateur);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update")
    public ResponseEntity<Utilisateur> updateUser(@RequestBody @Valid UserDto utilisateur) {
        Utilisateur updated = utilisateurService.upsertUser(utilisateur);
        return ResponseEntity.ok(updated);
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

}
