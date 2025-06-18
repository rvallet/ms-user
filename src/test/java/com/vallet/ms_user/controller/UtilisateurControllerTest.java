package com.vallet.ms_user.controller;

import com.vallet.ms_user.ApiRegistration;
import com.vallet.ms_user.TestUtils;
import com.vallet.ms_user.dto.UserDto;
import com.vallet.ms_user.model.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.vallet.ms_user.mock.UtilisateurMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles(profiles = { "test" })
public class UtilisateurControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private MongoOperations mongoOps;

    @Autowired
    private TestUtils testUtils;

    // Création des données de test
    final int utilisateurListSize = 5;
    final List<Utilisateur> utilisateurList = getUtilisateurListMock(utilisateurListSize);

    @AfterEach
    public void tearDown() {
        // Suppression des données de test
        mongoOps.dropCollection(Utilisateur.class);
    }

    @BeforeEach
    public void setUp() {
        // Création manuelle de l'index d'unicité du champ "login"
        mongoOps.indexOps(Utilisateur.class).createIndex(
                new Index().on("login", Sort.Direction.ASC).unique());

        // Enregistrement des données de test dans la base de données
        mongoOps.insert(utilisateurList, Utilisateur.class);
    }

    @Test
    public void testSetUp() {
        // Vérification de la création des données de test
        List<Utilisateur> utilisateurs = mongoOps.findAll(Utilisateur.class);

        // Vérification que les données de test ont été créées correctement
        assertNotNull(utilisateurs, "La liste des utilisateurs récupérée ne doit pas être null");
        assertFalse(utilisateurs.isEmpty(), "La liste des utilisateurs ne doit pas être vide");
        assertEquals(utilisateurList.size(), utilisateurs.size(),
                "Le nombre d'utilisateurs dans la base de données doit correspondre au nombre d'utilisateurs mockés");
    }

    @Test
    public void createUtilisateurTest() throws Exception {
        // Création d'un nouvel utilisateur
        UserDto newUser = getUserDtoMock();

        // Envoi de la requête POST pour créer l'utilisateur

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(
                        post(ApiRegistration.REST_PREFIX + ApiRegistration.CREATE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(testUtils.convertObjectToJson(newUser))
                )
                .andExpect(status().isCreated())
                .andReturn();
        // @formatter:on

        // Récupération de la réponse
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Utilisateur responseUser = testUtils.convertStringToObject(contentAsString, Utilisateur.class);

        // Vérification que l'utilisateur a été créé avec succès
        assertNotNull(responseUser, "La réponse ne doit pas être null");
        assertEquals(newUser.getLogin(), responseUser.getLogin(), "Le login de l'utilisateur créé doit correspondre au login envoyé");

    }

    @Test
    public void createUtilisateurWithExistingLoginTest() throws Exception {
        // Création d'un nouvel utilisateur avec un login déjà existant
        UserDto newUser = getUserDtoMock();

        // On récupère un login existant pour le nouvel utilisateur
        Utilisateur existingUser = mongoOps.findOne(
                new org.springframework.data.mongodb.core.query.Query(),
                Utilisateur.class);
        assertNotNull(existingUser, "L'utilisateur existant ne doit pas être null");
        newUser.setLogin(existingUser.getLogin());

        // Envoi de la requête POST pour créer l'utilisateur
        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(
                        post(ApiRegistration.REST_PREFIX + ApiRegistration.CREATE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(testUtils.convertObjectToJson(newUser))
                )
                .andExpect(status().isCreated())
                .andReturn();
        // @formatter:on

        // Récupération de la réponse
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Utilisateur responseUser = testUtils.convertStringToObject(contentAsString, Utilisateur.class);

        // Vérification que l'utilisateur a été mis à jour avec succès
        assertNotNull(responseUser, "La réponse ne doit pas être null");
        assertEquals(existingUser.getLogin(), responseUser.getLogin(),
                "Le login de l'utilisateur doit correspondre au login existant");
        assertEquals(existingUser.getId(), responseUser.getId(),
                "L'ID de l'utilisateur doit correspondre à l'ID de l'utilisateur existant");
        assertFalse(responseUser.getAdresses().isEmpty(),
                "La liste des adresses de l'utilisateur mis à jour ne doit pas être vide");
        assertFalse(responseUser.getNom().equalsIgnoreCase(existingUser.getNom()),
                "Le nom de l'utilisateur mis à jour doit être différent de celui de l'utilisateur existant");
        assertFalse(responseUser.getPrenom().equalsIgnoreCase(existingUser.getPrenom()),
                "Le nom de l'utilisateur mis à jour doit être différent de celui de l'utilisateur existant");
        assertFalse(responseUser.getCommonData() != null && responseUser.getCommonData().getCreatedAt() == null,
                "La date de création de l'utilisateur mis à jour ne doit pas être null");
        assertFalse(responseUser.getCommonData() != null && responseUser.getCommonData().getUpdatedAt() == null,
                "La date de création de l'utilisateur mis à jour ne doit pas être null");
        assertTrue(responseUser.getCommonData().getUpdatedAt().isAfter(existingUser.getCommonData().getUpdatedAt()),
                "La date de mise à jour de l'utilisateur mis à jour doit être postérieure à celle de l'utilisateur existant");
        assertTrue(responseUser.getCommonData().getCreatedAt().isEqual(existingUser.getCommonData().getCreatedAt()),
                "La date de création de l'utilisateur mis à jour doit être égale à celle de l'utilisateur existant");
    }

    @Test
    public void updateUtilisateurWithExistingLoginTest() throws Exception {
        // Création d'un nouvel utilisateur avec un login déjà existant
        UserDto newUser = getUserDtoMock();

        // On récupère un login existant pour le nouvel utilisateur
        Utilisateur existingUser = mongoOps.findOne(
                new org.springframework.data.mongodb.core.query.Query(),
                Utilisateur.class);
        assertNotNull(existingUser, "L'utilisateur existant ne doit pas être null");
        newUser.setLogin(existingUser.getLogin());

        // Envoi de la requête POST pour créer l'utilisateur
        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(
                        put(ApiRegistration.REST_PREFIX + ApiRegistration.UPDATE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(testUtils.convertObjectToJson(newUser))
                )
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        // Récupération de la réponse
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Utilisateur responseUser = testUtils.convertStringToObject(contentAsString, Utilisateur.class);

        // Vérification que l'utilisateur a été mis à jour avec succès
        assertNotNull(responseUser, "La réponse ne doit pas être null");
        assertEquals(existingUser.getLogin(), responseUser.getLogin(),
                "Le login de l'utilisateur doit correspondre au login existant");
        assertEquals(existingUser.getId(), responseUser.getId(),
                "L'ID de l'utilisateur doit correspondre à l'ID de l'utilisateur existant");
        assertFalse(responseUser.getAdresses().isEmpty(),
                "La liste des adresses de l'utilisateur mis à jour ne doit pas être vide");
        assertFalse(responseUser.getNom().equalsIgnoreCase(existingUser.getNom()),
                "Le nom de l'utilisateur mis à jour doit être différent de celui de l'utilisateur existant");
        assertFalse(responseUser.getPrenom().equalsIgnoreCase(existingUser.getPrenom()),
                "Le nom de l'utilisateur mis à jour doit être différent de celui de l'utilisateur existant");
        assertFalse(responseUser.getCommonData() != null && responseUser.getCommonData().getCreatedAt() == null,
                "La date de création de l'utilisateur mis à jour ne doit pas être null");
        assertFalse(responseUser.getCommonData() != null && responseUser.getCommonData().getUpdatedAt() == null,
                "La date de création de l'utilisateur mis à jour ne doit pas être null");
        assertTrue(responseUser.getCommonData().getUpdatedAt().isAfter(existingUser.getCommonData().getUpdatedAt()),
                "La date de mise à jour de l'utilisateur mis à jour doit être postérieure à celle de l'utilisateur existant");
        assertTrue(responseUser.getCommonData().getCreatedAt().isEqual(existingUser.getCommonData().getCreatedAt()),
                "La date de création de l'utilisateur mis à jour doit être égale à celle de l'utilisateur existant");

    }

}
