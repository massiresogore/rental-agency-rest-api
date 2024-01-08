package com.msr.rentalagency.location;

import com.msr.rentalagency.clientuser.ClientUser;
import com.msr.rentalagency.vehicule.Vehicule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer   id;

    @ManyToOne
    @JoinColumn( name = "id_client", nullable = false )
    private ClientUser client;

    @ManyToOne
    @JoinColumn( name = "id_vehicle", nullable = false )
    private Vehicule vehicle;

    @NotEmpty(message = "dateDebut is required")
    private Date dateDebut;
    @NotEmpty(message = "dateFin is required")
    private Date dateFin;
    @NotEmpty(message = "total is required")
    private double total;
    @NotEmpty(message = "dateReservation is required")
    private LocalDate dateReservation = LocalDate.now();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientUser getClient() {
        return client;
    }

    public void setClient(ClientUser client) {
        this.client = client;
    }

    public Vehicule getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicule vehicle) {
        this.vehicle = vehicle;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }
}
