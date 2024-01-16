package com.msr.rentalagency.vehicule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.rentalagency.agence.Agence;
import com.msr.rentalagency.system.StatusCode;
import com.msr.rentalagency.system.exception.ObjectNotFoundException;
import com.msr.rentalagency.vehicule.dto.VehiculeDto;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class VehiculeControllerTest {

    @MockBean
    VehiculeService vehiculeService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    List<Vehicule> vehicules = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Vehicule vehicule1 = new Vehicule();
        vehicule1.setId(1);
        vehicule1.setMarque("Twingo");
        vehicule1.setModele("Zoro");
        vehicule1.setPrix_journalier(12.2);
        vehicule1.setImage("vehicule11.jpg");
        vehicule1.setDescription("Jolie véhicule");
        //Agence
        Agence a1= new Agence();
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");
        //Set Agence
        vehicule1.setAgence(a1);
        //Add to list
        vehicules.add(vehicule1);

        Vehicule vehicule2 = new Vehicule();
        vehicule2.setId(2);
        vehicule2.setMarque("Equi");
        vehicule2.setModele("Lala");
        vehicule2.setPrix_journalier(89.0);
        vehicule2.setImage("vehicule2.jpg");
        vehicule2.setDescription("Ah ouai");
        //Agence
        Agence a2= new Agence();
        a2.setNom("Amazon");
        a2.setVille("Paris");
        a2.setAdresse("22 rue des pite");
        a2.setCp(7989);
        a2.setTel("09090909");
        a2.setImage("image.jpg");
        a2.setEmail("amazon&gmail.com");
        //Set Agence
        vehicule2.setAgence(a2);
        //Add to list
        vehicules.add(vehicule2);

        Vehicule vehicule3 = new Vehicule();
        vehicule3.setId(3);
        vehicule3.setMarque("Piole");
        vehicule3.setModele("lier");
        vehicule3.setPrix_journalier(23.0);
        vehicule3.setImage("vehicule3.jpg");
        vehicule3.setDescription("Jolie 3");
        //Agence
        Agence a3= new Agence();
        a3.setNom("Amazon");
        a3.setVille("Paris");
        a3.setAdresse("22 rue des pite");
        a3.setCp(7989);
        a3.setTel("09090909");
        a3.setImage("image.jpg");
        a3.setEmail("amazon&gmail.com");
        //Set Agence
        vehicule3.setAgence(a3);
        //Add to list
        vehicules.add(vehicule3);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addVehiculeSuccess() throws Exception {

        VehiculeDto vehiculeDto = new VehiculeDto(
                null,
                "Tesla",
                "V-X",
                12.00,
                "tres jolie",
                "image2.jpg",
                1
        );

        Agence a1= new Agence();
        a1.setId(1);
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");

String json = objectMapper.writeValueAsString(vehiculeDto);
        //Given
        Vehicule savedVehicule = new Vehicule();
        savedVehicule.setId(1);
        savedVehicule.setMarque("Tesla");
        savedVehicule.setModele("V-X");
        savedVehicule.setPrix_journalier(12.00);
        savedVehicule.setImage("image2.jpg");
        savedVehicule.setDescription("tres jolie");
        savedVehicule.setAgence(a1);


        given(this.vehiculeService.save(Mockito.any(Vehicule.class))).willReturn(savedVehicule);


        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post(baseUrl+"/vehicules")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Create vehicule success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.marque").value(savedVehicule.getMarque()))
                .andExpect(jsonPath("$.data.modele").value(savedVehicule.getModele()))
                .andExpect(jsonPath("$.data.prix_journalier").value(savedVehicule.getPrix_journalier()))
                .andExpect(jsonPath("$.data.image").value(savedVehicule.getImage()));
    }

    @Test
    void testFindAllSuccess() throws Exception {
        //Given
        given(this.vehiculeService.findAllVehicule()).willReturn(this.vehicules);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/vehicules")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find all success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.vehicules.size())))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].marque").value("Twingo"))
                .andExpect(jsonPath("$.data[0].image").value("vehicule11.jpg"))
                .andExpect(jsonPath("$.data[0].modele").value("Zoro"));
    }

    @Test
    void testFindByIdSuccess() throws Exception {
        //Given
        given(this.vehiculeService.findOneVehiculeById(1)).willReturn(this.vehicules.get(0));

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/vehicules/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find vehicule success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.marque").value("Twingo"))
                .andExpect(jsonPath("$.data.image").value("vehicule11.jpg"))
                .andExpect(jsonPath("$.data.modele").value("Zoro"));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        //Given
        given(this.vehiculeService.findOneVehiculeById(99)).willThrow(new ObjectNotFoundException("vehicule",99));

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/vehicules/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find vehicule with id:99 :)"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteWithIdNotFound() throws Exception {
        //Given
doThrow(new ObjectNotFoundException("vehicule",99)).when(this.vehiculeService).deleteVehicule(99);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl+"/vehicules/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find vehicule with id:99 :)"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        //Given
        doNothing().when(this.vehiculeService).deleteVehicule(1);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl+"/vehicules/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete vehicule success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testUpdateSuccess() throws Exception {

        VehiculeDto vehiculeDto = new VehiculeDto(
                1,
                "Toyota",
                "Gt-gang",
                120.00,
                "Effrakata",
                "effrakata.jpg",
                1
        );

        String json = objectMapper.writeValueAsString(vehiculeDto);

        Vehicule vehiculeUpdated = new Vehicule();
        vehiculeUpdated.setId(1);
        vehiculeUpdated.setPrix_journalier(12.000);
        vehiculeUpdated.setMarque("ToyotaUpdate");
        vehiculeUpdated.setModele("Gt-gangUpdate");
        vehiculeUpdated.setDescription("EffrakataUpdate");
        vehiculeUpdated.setImage("effrakataUpdate.jpg");

        Agence a1= new Agence();
        a1.setId(1);
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");

        vehiculeUpdated.setAgence(a1);
        //Given
        given(this.vehiculeService.updateVehicule(eq(1), Mockito.any(Vehicule.class))).willReturn(vehiculeUpdated);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/vehicules/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update vehicule success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.marque").value("ToyotaUpdate"))
                .andExpect(jsonPath("$.data.image").value("effrakataUpdate.jpg"))
                .andExpect(jsonPath("$.data.modele").value("Gt-gangUpdate"));
    }

    @Test
    void testUpdateWithNoExistantId() throws Exception {
        //Given

        VehiculeDto vehiculeDto = new VehiculeDto(
                null,
                "Toyota",
                "Gt-gang",
                120.00,
                "Effrakata",
                "effrakata.jpg",
                1
        );

        String json = objectMapper.writeValueAsString(vehiculeDto);
        given(this.vehiculeService.updateVehicule(eq(99),Mockito.any(Vehicule.class)))
                .willThrow(new ObjectNotFoundException("vehicule",99));


        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.put(baseUrl+"/vehicules/99")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find vehicule with id:99 :)"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}