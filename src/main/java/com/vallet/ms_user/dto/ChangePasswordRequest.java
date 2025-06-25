package com.vallet.ms_user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.vallet.ms_user.service.impl.UtilisateurServiceImpl.DEFAULT_PASSWORD_REGEX;

/**
 * Représente une requête pour changer le mot de passe d'un utilisateur.
 * Contient les informations nécessaires pour identifier l'utilisateur et le nouveau mot de passe.
 */
public class ChangePasswordRequest {

    @Schema(description = "email de l'utilisateur",
            example = "johndoe@email.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;

    @Schema(description = "Nouveau mot de passe respectant les règles définies par l'expression régulière",
            example = "newSecurePassword@123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;

    @Schema(description = "Expression régulière pour valider le mot de passe (optionnelle, applique la validation par défaut en example si non spécifiée)",
            example = DEFAULT_PASSWORD_REGEX,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String passwordRegex;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String login, String newPassword, String passwordRegex) {
        this.login = login;
        this.newPassword = newPassword;
        this.passwordRegex = passwordRegex;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordRegex() {
        return passwordRegex;
    }

    public void setPasswordRegex(String passwordRegex) {
        this.passwordRegex = passwordRegex;
    }

}
