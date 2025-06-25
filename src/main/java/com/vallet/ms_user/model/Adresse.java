package com.vallet.ms_user.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class Adresse {

    private String numero;
    private String rue;
    private String codePostal;
    private String ville;
    private String pays;
    @Schema(description = "Indique si l'adresse est principale",
            example = "true",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean principale;

    public Adresse() {
    }

    public Adresse(String numero, String rue, String codePostal, String ville, String pays) {
        this.numero = numero;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public boolean getPrincipale() {
        return principale;
    }

    public void setPrincipale(boolean isPrincipale) {
        this.principale = isPrincipale;
    }

}
