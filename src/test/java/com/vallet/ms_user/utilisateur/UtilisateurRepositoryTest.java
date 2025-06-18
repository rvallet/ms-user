package com.vallet.ms_user.utilisateur;

import com.vallet.ms_user.model.Adresse;
import com.vallet.ms_user.model.CommonData;
import com.vallet.ms_user.model.Utilisateur;
import com.vallet.ms_user.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    void testCreateAndFind() {

        CommonData commonData = new CommonData();
        commonData.setCreatedAt(java.time.LocalDateTime.now());
        commonData.setCreatedBy("testUser");
        commonData.setUpdatedAt(java.time.LocalDateTime.now());
        commonData.setUpdatedBy("testUser");

        Adresse adresse = new Adresse();
        adresse.setRue("123 Main St");
        adresse.setVille("Testville");
        adresse.setCodePostal("12345");
        adresse.setPays("Testland");
        adresse.setNumero("1");
        adresse.setPrincipale(true);


        Utilisateur user = new Utilisateur();
        user.setLogin("test");
        user.setMotDePasse("pass");
        user.setNom("Doe");
        user.setPrenom("John");
        user.setAdresses(List.of(adresse));
        user.setCommonData(commonData);

        Utilisateur savedUser = utilisateurRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getLogin()).isEqualTo("test");
        assertThat(savedUser.getNom()).isEqualTo("Doe");
        assertThat(savedUser.getPrenom()).isEqualTo("John");
        assertThat(savedUser.getAdresses()).isNotEmpty();
        assertThat(savedUser.getAdresses().get(0).getRue()).isEqualTo("123 Main St");
        assertThat(savedUser.getCommonData()).isNotNull();
        assertThat(savedUser.getCommonData().getCreatedBy()).isEqualTo("testUser");
        assertThat(savedUser.getCommonData().getUpdatedBy()).isEqualTo("testUser");
        assertThat(savedUser.getCommonData().getCreatedAt()).isNotNull();
        assertThat(savedUser.getCommonData().getUpdatedAt()).isNotNull();

    }

}
