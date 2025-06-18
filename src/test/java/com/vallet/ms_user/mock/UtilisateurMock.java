package com.vallet.ms_user.mock;

import com.vallet.ms_user.dto.UserDto;
import com.vallet.ms_user.model.Adresse;
import com.vallet.ms_user.model.CommonData;
import com.vallet.ms_user.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurMock {

    public static final String ID = "1234567890";
    public static final String LOGIN = "user@test.com";
    public static final String MOT_DE_PASSE = "password123";

    public static final String NOM = "Test";
    public static final String PRENOM = "User";

    public static UserDto getUserDtoMock() {
        UserDto userDto = new UserDto();
        userDto.setLogin(LOGIN);
        userDto.setMotDePasse(MOT_DE_PASSE);
        userDto.setNom(NOM);
        userDto.setPrenom(PRENOM);
        userDto.setAdresses(getAdresseListMock(2));
        return userDto;
    }

    public static Utilisateur getUtilisateurMock() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(ID);
        utilisateur.setLogin(LOGIN);
        utilisateur.setMotDePasse(MOT_DE_PASSE);
        utilisateur.setNom(NOM);
        utilisateur.setPrenom(PRENOM);
        utilisateur.setCommonData(new CommonData());
        utilisateur.setAdresses(getAdresseListMock(2));
        return utilisateur;
    }

    public static List<Utilisateur> getUtilisateurListMock(int size) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(ID + "_" + i);
            utilisateur.setLogin(LOGIN + "_" + i);
            utilisateur.setMotDePasse(MOT_DE_PASSE + "_" + i);
            utilisateur.setNom(NOM + "_" + i);
            utilisateur.setPrenom(PRENOM + "_" + i);
            utilisateur.setAdresses(getAdresseListMock(2));
            utilisateur.setCommonData(new CommonData());
            utilisateurs.add(utilisateur);
        }
        return utilisateurs;
    }

    public static List<UserDto> getUserDtoListMock(int size) {
        List<UserDto> userDtos = new ArrayList<>();
        for (int i = 1; i == size; i++) {
            UserDto userDto = new UserDto();
            userDto.setLogin(LOGIN + "_" + i);
            userDto.setMotDePasse(MOT_DE_PASSE + "_" + i);
            userDto.setNom(NOM + "_" + i);
            userDto.setPrenom(PRENOM + "_" + i);
            userDto.setAdresses(getAdresseListMock(2));
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public static Adresse getAdresseMock(boolean isPrincipale, int index) {
        Adresse adresse = new Adresse();
        adresse.setRue("123 Test Street_" + index);
        adresse.setVille("Test City_" + index);
        adresse.setCodePostal("12345_" + index);
        adresse.setPays("Testland_" + index);
        adresse.setPrincipale(isPrincipale);
        return adresse;
    }

    public static List<Adresse> getAdresseListMock(int size) {
        List<Adresse> adresses = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Adresse adresse = getAdresseMock(i == 1, i);
            adresses.add(adresse);
        }
        return adresses;
    }

}
