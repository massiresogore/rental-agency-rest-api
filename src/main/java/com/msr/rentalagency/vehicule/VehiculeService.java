package com.msr.rentalagency.vehicule;

import com.msr.rentalagency.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;

    }

    public Vehicule save(Vehicule vehicule) {
      return   this.vehiculeRepository.save(vehicule);
    }

    public List<Vehicule> findAllVehicule()
    {
        List<Vehicule> vehicules = new ArrayList<>();
        vehicules = this.vehiculeRepository.findAll().stream().toList();
            return vehicules;
    }

    public Vehicule findOneVehiculeById(int vehiculeId)
    {
        return  this.vehiculeRepository.findById(vehiculeId).orElseThrow(()->new ObjectNotFoundException("vehicule",vehiculeId));
    }

    public void deleteVehicule(int vehiculeId) {
       Vehicule vehicule =  this.vehiculeRepository.findById(vehiculeId).orElseThrow(()->new ObjectNotFoundException("vehicule",vehiculeId));
       this.vehiculeRepository.delete(vehicule);
    }

    public Vehicule updateVehicule(int vehiculeId, Vehicule update)
    {
       return   this.vehiculeRepository.findById(vehiculeId)
                 .map(oldVehicule->{
                     oldVehicule.setDescription(update.getDescription());
                     oldVehicule.setModele(update.getModele());
                     oldVehicule.setImage(update.getImage());
                     oldVehicule.setMarque(update.getMarque());
                     oldVehicule.setPrix_journalier(update.getPrix_journalier());
                     return this.vehiculeRepository.save(oldVehicule);
                 })
                 .orElseThrow(()->new ObjectNotFoundException("vehicule",vehiculeId));
    }
}
