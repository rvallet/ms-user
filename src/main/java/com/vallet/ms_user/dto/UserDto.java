package com.vallet.ms_user.dto;

import com.vallet.ms_user.model.Adresse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UserDto {

    @NotNull
    @NotBlank
    @Schema(description = "email de l'utilisateur",
            example = "johndoe@email.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;

    @Schema(description = "Mot de passe respectant les règles définies par l'expression régulière",
            example = "newSecurePassword@123",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String motDePasse;

    private String nom;
    private String prenom;

    @Schema(description = "Liste des adresses associées à l'utilisateur",
            example = "[{numero: '123', rue: 'Main St', codePostal: '12345', ville: 'Paris', pays: 'France', principale: true}, {numero: '456', rue: 'Second St', codePostal: '67890', ville: 'Lyon', pays: 'France', principale: false}]",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<Adresse> adresses;

    public UserDto() {
    }

    public UserDto(String login, String motDePasse, String nom, String prenom, List<Adresse> adresses) {
        this.login = login;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.adresses = adresses;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

}
