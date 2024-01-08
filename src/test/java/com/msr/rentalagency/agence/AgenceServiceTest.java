package com.msr.rentalagency.agence;

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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgenceServiceTest {

    @Mock
    AgenceRepository agenceRepository;

    @InjectMocks
    AgenceService agenceService;

    List<Agence> agences = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Agence a1= new Agence();
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");
        agences.add(a1);

        Agence a2= new Agence();
        a2.setNom("BintaShop");
        a2.setVille("Noisy");
        a2.setAdresse("123 avenu fosh");
        a2.setCp(88888);
        a2.setTel("09876554");
        a2.setImage("binta.jpg");
        a2.setEmail("binta&gmail.com");
        agences.add(a2);

        Agence a3= new Agence();
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
    void testgetAllAgence() {
        //Given
        given(this.agenceRepository.findAll()).willReturn(this.agences);

        //When
        List<Agence> actualAgences = this.agenceService.getAllAgence();

        //Then
        assertThat(actualAgences.size()).isEqualTo(this.agences.size());

        //Vérifie agencerepository.findAll() qu'il soit  exécuté une foi
        verify(this.agenceRepository,times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess()
    {
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
        given(this.agenceRepository.findById(1)).willReturn(Optional.of(a1));

        //When
        Agence returnedAgence = this.agenceService.findById(1);

        //then
        assertThat(returnedAgence.getId()).isEqualTo(a1.getId());
        assertThat(returnedAgence.getNom()).isEqualTo(a1.getNom());

        verify(agenceRepository,times(1)).findById(1);

    }

    @Test
    void testFindAgenceByIdNotFound()
    {
        //Given
        given(this.agenceRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //When
        Throwable throwns = catchThrowable(()-> {
            Agence returneAgence = this.agenceService.findById(5);
        });

        //Then
        assertThat(throwns)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find agence with id:5 :)");
        verify(agenceRepository,times(1)).findById(Mockito.any(Integer.class));
    }

    @Test
    void testSaveAgenceSuccess()
    {
        Agence a1= new Agence();
        a1.setNom("Amazon");
        a1.setVille("Paris");
        a1.setAdresse("22 rue des pite");
        a1.setCp(7989);
        a1.setTel("09090909");
        a1.setImage("image.jpg");
        a1.setEmail("amazon&gmail.com");

        //Given
        given(this.agenceRepository.save(a1)).willReturn(a1);

        //When
        Agence returnedAgence = this.agenceService.save(a1);

        //Then
        assertThat(returnedAgence.getNom()).isEqualTo(a1.getNom());

        verify(this.agenceRepository,times(1)).save(a1);
    }

    @Test
    void testUpdateAgenceSuccess()
    {

        Agence oldAgence= new Agence();
        oldAgence.setId(1);
        oldAgence.setNom("Amazon");
        oldAgence.setVille("Paris");
        oldAgence.setAdresse("22 rue des pite");
        oldAgence.setCp(7989);
        oldAgence.setTel("09090909");
        oldAgence.setImage("image.jpg");
        oldAgence.setEmail("amazon&gmail.com");

        Agence updateAgence = new Agence();
        updateAgence.setNom("update nom");

        given(this.agenceRepository.findById(1)).willReturn(Optional.of(oldAgence));
        given(this.agenceRepository.save(oldAgence)).willReturn(oldAgence);

        //When
        Agence savededAgence = this.agenceService.update(1,updateAgence);

        //Then
        assertThat(savededAgence.getId()).isEqualTo(1);
        assertThat(savededAgence.getNom()).isEqualTo(updateAgence.getNom());

        verify(this.agenceRepository,times(1)).findById(1);
        verify(this.agenceRepository,times(1)).save(oldAgence);
    }

    @Test
    void testUpdateNotFound()
    {
        Agence updateAgence = new Agence();
        updateAgence.setNom("Update new name");
        //Given
        given(this.agenceRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class,()->{
            Agence agenceWilupdate = this.agenceService.update(1,updateAgence);
        });

        //Then
        verify(this.agenceRepository,times(1)).findById(1);
    }

    @Test
    void testdeleteSuccess()
    {
        Agence oldAgence= new Agence();
        oldAgence.setId(1);
        oldAgence.setNom("Amazon");
        oldAgence.setVille("Paris");
        oldAgence.setAdresse("22 rue des pite");
        oldAgence.setCp(7989);
        oldAgence.setTel("09090909");
        oldAgence.setImage("image.jpg");
        oldAgence.setEmail("amazon&gmail.com");

        //Given, lorsquon donne
        given(this.agenceRepository.findById(1)).willReturn(Optional.of(oldAgence));
        doNothing().when(this.agenceRepository).deleteById(1);

        //when , quand on supprime
        this.agenceService.delete(1);

        //then
        verify(this.agenceRepository,times(1)).deleteById(1);
    }

    @Test
    void testDeleteNotFound()
    {
        //Given
        given(this.agenceRepository.findById(9)).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class,()->{
            this.agenceService.delete(9);
        });

        //Then
        verify(this.agenceRepository,times(1)).findById(9);

    }
}