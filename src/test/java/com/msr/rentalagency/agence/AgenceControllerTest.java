package com.msr.rentalagency.agence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.rentalagency.agence.dto.AgenceDto;
import com.msr.rentalagency.system.StatusCode;
import com.msr.rentalagency.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AgenceControllerTest {
    @MockBean
    AgenceService agenceService;

    @Autowired
    MockMvc mockMvc;

    @Value("${api.endpoint.base-url}")
    String baseUrl;
    @Autowired
    ObjectMapper objectMapper;

    List<Agence> agences = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Agence a1= new Agence();
        a1.setId(1);
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");
        agences.add(a1);

        Agence a2= new Agence();
        a2.setId(2);
        a2.setNom("BintaShop");
        a2.setVille("Noisy");
        a2.setAdresse("123 avenu fosh");
        a2.setCp(88888);
        a2.setTel("09876554");
        a2.setImage("binta.jpg");
        a2.setEmail("binta&gmail.com");
        agences.add(a2);

        Agence a3= new Agence();
        a3.setId(3);
        a3.setNom("Sita shop");
        a3.setVille("Lizy Sur Orcq");
        a3.setAdresse("90 allé de marché");
        a3.setCp(5454);
        a3.setTel("76555555");
        a3.setImage("sita.jpg");
        a3.setEmail("sita&gmail.com");
        agences.add(a3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testgetAllAgenceSuccess() throws Exception {
        //Given
        given(this.agenceService.getAllAgence()).willReturn(this.agences);

        //When and Then
        this.mockMvc.perform(get(this.baseUrl+"/agences").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find all success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nom").value("Amazon"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].nom").value("BintaShop"));
    }

    @Test
    void testFindByIdSuccess() throws Exception {
        Agence a1= new Agence();
        a1.setId(1);
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");
        //Given
        given(agenceService.findById(1)).willReturn(a1);

        //When and then
        this.mockMvc.perform(get(this.baseUrl+"/agences/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find one success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(a1.getId()))
                .andExpect(jsonPath("$.data.nom").value(a1.getNom()));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        //given
        given(agenceService.findById(5)).willThrow(new ObjectNotFoundException("agence",5));

        //When and Then
        this.mockMvc.perform(get(this.baseUrl+"/agences/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find agence with id:5 :)"));
    }

    @Test
    void testSaveSuccess() throws Exception {
        AgenceDto agenceDto = new AgenceDto(null,
                "Amazon",
                "Paris",
                "amazon&gmail.com",
                "14 rue bold",
                7788,
                "Paris",
                "amazon.jpg",
                null
                );

        String json = this.objectMapper.writeValueAsString(agenceDto);

        Agence savedAgence= new Agence();
        savedAgence.setId(agenceDto.id());
        savedAgence.setNom(agenceDto.nom());
        savedAgence.setVille(agenceDto.ville());
        savedAgence.setAdresse(agenceDto.adresse());
        savedAgence.setCp(agenceDto.cp());
        savedAgence.setTel(agenceDto.tel());
        savedAgence.setImage(agenceDto.image());
        savedAgence.setEmail(agenceDto.email());

        //Given
        given(this.agenceService.save(Mockito.any(Agence.class))).willReturn(savedAgence);

        //When and Then
        this.mockMvc.perform(post(this.baseUrl+"/agences")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Create agence success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(savedAgence.getId()))
                .andExpect(jsonPath("$.data.nom").value(savedAgence.getNom()));
    }

    @Test
    void testUpdateSuccess() throws Exception {
        //ce qu'on envoie
        AgenceDto agenceDto = new AgenceDto(null,
                "Amazon",
                "Paris",
                "amazon&gmail.com",
                "14 rue bold",
                7788,
                "Paris",
                "amazon.jpg",
                null
        );
        //récupérer ce qui a à été envoyé pour la mis a jour depuis la base de donnée
        Agence Update = new Agence();
        Update.setId(1);
        Update.setNom(agenceDto.nom());
        Update.setVille(agenceDto.ville());
        Update.setAdresse(agenceDto.adresse());
        Update.setCp(agenceDto.cp());
        Update.setTel(agenceDto.tel());
        Update.setImage(agenceDto.image());
        Update.setEmail(agenceDto.email());

        //le covertir en json
        String json = this.objectMapper.writeValueAsString(Update);

        //Given
        //Si On donne
        given(this.agenceService.update(eq(1),Mockito.any(Agence.class))).willReturn(Update);

        //When and Then
        this.mockMvc.perform(put(this.baseUrl+"/agences/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(Update.getId()))
                .andExpect(jsonPath("$.data.nom").value(Update.getNom()));
    }

    @Test
    void updateNotFound() throws Exception {
        //ce qu'on envoie
        AgenceDto agenceDto = new AgenceDto(9,
                "Amazon",
                "Paris",
                "amazon&gmail.com",
                "14 rue bold",
                7788,
                "Paris",
                "amazon.jpg",
                null
        );

        String json = this.objectMapper.writeValueAsString(agenceDto);

        //Given
        given(this.agenceService.update(eq(9),Mockito.any(Agence.class)))
                .willThrow(new ObjectNotFoundException("agence",9));


        //When and Then
        this.mockMvc.perform(put(this.baseUrl+"/agences/9")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find agence with id:9 :)"));
    }

    @Test
    void testDeleteSuccess() throws Exception {
        //Given
       doNothing().when(this.agenceService).delete(1);

        //When and Then
        this.mockMvc.perform(delete(this.baseUrl+"/agences/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Detele agence success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteNotExistentId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("agence",9)).when(this.agenceService).delete(9);

        //When and Then
        this.mockMvc.perform(delete(this.baseUrl+"/agences/9")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find agence with id:9 :)"));
    }

}