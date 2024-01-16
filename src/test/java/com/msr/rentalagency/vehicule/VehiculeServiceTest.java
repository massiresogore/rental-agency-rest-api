package com.msr.rentalagency.vehicule;

import com.msr.rentalagency.agence.Agence;
import com.msr.rentalagency.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceTest {
    @Mock
    VehiculeRepository vehiculeRepository;
    @InjectMocks
    VehiculeService vehiculeService;

    List<Vehicule> vehicules = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Vehicule vehicule1 = new Vehicule();
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
        vehicule2.setMarque("Twingo");
        vehicule2.setModele("Zoro");
        vehicule2.setPrix_journalier(12.2);
        vehicule2.setImage("vehicule21.jpg");
        vehicule2.setDescription("Jolie véhicule");
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
        vehicule3.setMarque("Twingo");
        vehicule3.setModele("Zoro");
        vehicule3.setPrix_journalier(12.2);
        vehicule3.setImage("vehicule31.jpg");
        vehicule3.setDescription("Jolie véhicule");
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
    void testSaveSuccess() {
        Vehicule vehicule = new Vehicule();
        vehicule.setMarque("Twingo");
        vehicule.setModele("Zoro");
        vehicule.setPrix_journalier(12.2);
        vehicule.setImage("vehicule1.jpg");
        vehicule.setDescription("Jolie véhicule");

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
        vehicule.setAgence(a1);

        //Given
        given(this.vehiculeRepository.save(vehicule)).willReturn(vehicule);

        //When
        Vehicule savedVehicule = this.vehiculeService.save(vehicule);

        //Then
        assertThat(savedVehicule.getDescription()).isEqualTo(vehicule.getDescription());

        verify(this.vehiculeRepository,times(1)).save(vehicule);

    }

    @Test
    void testFindAllVehicule()
    {
        //Given
        given(this.vehiculeRepository.findAll()).willReturn(this.vehicules);

        //When
        List<Vehicule> actualVehicule = this.vehiculeService.findAllVehicule();

        //Then
        assertThat(actualVehicule.size()).isEqualTo(this.vehicules.size());

        verify(this.vehiculeRepository,times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess()
    {
        Vehicule vehicule1 = new Vehicule();
        vehicule1.setId(1);
        vehicule1.setMarque("Twingo");
        vehicule1.setModele("Zoro");
        vehicule1.setPrix_journalier(12.2);
        vehicule1.setImage("vehicule11.jpg");
        vehicule1.setDescription("Jolie véhicule");

        Agence a1= new Agence();
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");

        vehicule1.setAgence(a1);
        //Given
        given(this.vehiculeRepository.findById(1)).willReturn(Optional.of(vehicule1));

        //When
        Vehicule actualvehiculeFound = this.vehiculeService.findOneVehiculeById(1);

        //Then
        assertThat(actualvehiculeFound.getDescription()).isEqualTo(vehicule1.getDescription());
        assertThat(actualvehiculeFound.getImage()).isEqualTo(vehicule1.getImage());
        assertThat(actualvehiculeFound.getMarque()).isEqualTo(vehicule1.getMarque());
        assertThat(actualvehiculeFound.getMarque()).isEqualTo(vehicule1.getMarque());

        verify(this.vehiculeRepository,times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound()
    {
        //Given
        given(this.vehiculeRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(()->{
            Vehicule vehicule = this.vehiculeService.findOneVehiculeById(99);
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find vehicule with id:99 :)");

        verify(this.vehiculeRepository,times(1)).findById(Mockito.any(Integer.class));
    }

    @Test
    void testDeleteSuccess()
    {
        Vehicule vehicule1 = new Vehicule();
        vehicule1.setId(1);
        vehicule1.setMarque("Twingo");
        vehicule1.setModele("Zoro");
        vehicule1.setPrix_journalier(1.22);
        vehicule1.setImage("vehicule11.jpg");
        vehicule1.setDescription("Jolie véhicule");

        Agence a1= new Agence();
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");

        vehicule1.setAgence(a1);

        //Given
        given(this.vehiculeRepository.findById(1)).willReturn(Optional.of(vehicule1));
        doNothing().when(this.vehiculeRepository).delete(vehicule1);

        //When
        vehiculeService.deleteVehicule(1);

        //Then
        verify(this.vehiculeRepository,times(1)).delete(vehicule1);
    }

    @Test
    void testDeleteWithIdNotFound()
    {
        //Given
        given(this.vehiculeRepository.findById(99)).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, ()-> {
           this.vehiculeService.deleteVehicule(99);
        });
        //Then
        verify(this.vehiculeRepository,times(1)).findById(99);
    }

    @Test
    void testUpdateSuccess()
    {


        Vehicule oldVehicule = new Vehicule();
        oldVehicule.setId(1);
        oldVehicule.setMarque("Twingo");
        oldVehicule.setModele("Zoro");
        oldVehicule.setPrix_journalier(12.2);
        oldVehicule.setImage("oldVehicule1.jpg");
        oldVehicule.setDescription("Jolie véhicule");

        Vehicule update = new Vehicule();
        update.setMarque("Twingo update");
        update.setModele("Zoro update");
        update.setPrix_journalier(98.777);

        //Given
        given(this.vehiculeRepository.findById(1)).willReturn(Optional.of(oldVehicule));
        given(this.vehiculeRepository.save(oldVehicule)).willReturn(oldVehicule);

        //When
        Vehicule vehiculeUpdate = this.vehiculeService.updateVehicule(1,update);

        //Then
        assertThat(vehiculeUpdate.getMarque()).isEqualTo(update.getMarque());
        assertThat(vehiculeUpdate.getModele()).isEqualTo(update.getModele());
        assertThat(vehiculeUpdate.getPrix_journalier()).isEqualTo(update.getPrix_journalier());

        verify(this.vehiculeRepository,times(1)).findById(1);
        verify(this.vehiculeRepository,times(1)).save(oldVehicule);
    }

    @Test
    void testUpdateNotFound()
    {
        //Given


        Vehicule update = new Vehicule();
        update.setMarque("Twingo update");
        update.setModele("Zoro update");
        update.setPrix_journalier(98.777);
        given(this.vehiculeRepository.findById(99)).willReturn(Optional.empty());


        //When
        assertThrows(ObjectNotFoundException.class,()->{
            this.vehiculeService.updateVehicule(99,update);
        });
        //Then
        verify(this.vehiculeRepository,times(1)).findById(99);
    }


}