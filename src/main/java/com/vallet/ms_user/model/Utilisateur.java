package com.vallet.ms_user.model;

import com.vallet.ms_user.exception.NotFoundException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Utilisateurs")
public class Utilisateur {

    @Id
    private String id;

    /**
     * Login utilisateur
     * Il doit être unique en BDD, il faut donc créer un index unique sur ce champ.
     * Dans MongoDB : db.Utilisateurs.createIndex({ login: 1 }, { unique: true })
     */
    @Indexed(unique = true)
    private String login;

    private String motDePasse;
    private String nom;
    private String prenom;
    private List<Adresse> adresses;
    private CommonData commonData;

    public Utilisateur() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CommonData getCommonData() {
        return commonData;
    }

    public void setCommonData(CommonData commonData) {
        this.commonData = commonData;
    }

    public Adresse getAdressePrincipale() {
        if (adresses != null && !adresses.isEmpty()) {
            return adresses.stream()
                    .filter(Adresse::getPrincipale)
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Pas d'adresse principale trouvée pour l'utilisateur : " + getLogin()));
        }
        return null;
    }

}
