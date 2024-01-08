package com.msr.rentalagency.agence;

import com.msr.rentalagency.vehicule.Vehicule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
public class Agence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name is required")
    private String  nom;
    @NotEmpty(message = "tel is required")
    private String  tel;
    @NotEmpty(message = "email is required")
    private String  email;
    @NotEmpty(message = "adresse is required")
    private String  adresse;
    @NotNull(message = "cp is required")
    private int  cp;
    @NotEmpty(message = "ville is required")
    private String  ville;
    @NotEmpty(message = "image is required")
    private String  image;
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "agence")
    List<Vehicule> vehicules = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public void addVehicule(Vehicule vehicule)
    {
        //on attribut le véhicule à cette agence
        vehicule.setAgence(this);

        //on ajoute cette véhicule dans la liste de cette agence
        this.vehicules.add(vehicule);
    }

    public int getNomberOfVehicule()
    {
        return this.vehicules.size();
    }

    public void removeAllVehicule()
    {
        //on retire lagence au véhicule
        this.vehicules.stream().forEach(vehicule -> vehicule.setAgence(null));

        //on supprime le véhicule.
        this.vehicules = null;
    }

    public void removeVehicule(Vehicule vehiculeTobeAssigned)
    {
        vehiculeTobeAssigned.setAgence(null);
        this.vehicules.remove(vehiculeTobeAssigned);
    }

}
