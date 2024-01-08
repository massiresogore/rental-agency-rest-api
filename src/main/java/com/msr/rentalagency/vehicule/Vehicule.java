package com.msr.rentalagency.vehicule;

import com.msr.rentalagency.agence.Agence;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "vehicule")
public class Vehicule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String  marque;
    private String  modele;
    private double  prix_journalier;
    private String  description;
    private String  image;
    @ManyToOne
    @JoinColumn(nullable = false)
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

    public double getPrix_journalier() {
        return prix_journalier;
    }

    public void setPrix_journalier(double prix_journalier) {
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
