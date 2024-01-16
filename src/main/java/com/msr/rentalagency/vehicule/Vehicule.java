package com.msr.rentalagency.vehicule;

import com.msr.rentalagency.agence.Agence;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

@Entity
@Table(name = "vehicule")
public class Vehicule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "marque is required")
    private String  marque;
    @NotEmpty(message = "modele is required")
    private String  modele;
    @Min(value = 1,message = "Le prix ne peut pas être null")
    private Double  prix_journalier;
    @NotEmpty(message = "description is required")
    private String  description;
    private String  image;
    @ManyToOne
    @JoinColumn(nullable = false,name = "agence_id")
    private Agence agence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Double getPrix_journalier() {
        return prix_journalier;
    }

    public void setPrix_journalier(Double prix_journalier) {
        this.prix_journalier = prix_journalier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }



}
