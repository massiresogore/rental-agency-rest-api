package com.msr.rentalagency.vehicule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository  extends JpaRepository<Vehicule, Integer> {
}
