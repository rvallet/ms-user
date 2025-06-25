package com.vallet.ms_user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.vallet.ms_user.service.impl.UtilisateurServiceImpl.DEFAULT_PASSWORD_REGEX;

public class ChangePasswordRequest {

    @Schema(description = "Login de l'utilisateur",
            example = "johndoe",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;

    @Schema(description = "Nouveau mot de passe",
            example = "newSecurePassword123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;

    @Schema(description = "Expression régulière pour valider le mot de passe (optionnelle)",
            example = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            defaultValue = DEFAULT_PASSWORD_REGEX)
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
