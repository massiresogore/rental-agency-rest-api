package com.msr.rentalagency.agence;

import com.msr.rentalagency.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AgenceService {
    private final AgenceRepository agenceRepository;

    public AgenceService(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    public List<Agence> getAllAgence() {
        return this.agenceRepository.findAll();
    }

    public Agence findById(Integer agenceId) {
        return this.agenceRepository.findById(agenceId).orElseThrow(()->new ObjectNotFoundException("agence",agenceId));
    }

    public void delete(Integer agenceId) {
        this.agenceRepository.findById(agenceId).orElseThrow(()->new ObjectNotFoundException("agence",agenceId));
        this.agenceRepository.deleteById(agenceId);
    }

    public Agence save(Agence agence) {

        return this.agenceRepository.save(agence);
    }

    public Agence update(Integer agenceId, Agence update) {

     return    this.agenceRepository.findById(agenceId).map(oldAgence->{
            oldAgence.setNom(update.getNom());
            oldAgence.setAdresse(update.getAdresse());
            oldAgence.setCp(update.getCp());
            oldAgence.setEmail(update.getEmail());
            oldAgence.setTel(update.getTel());
            oldAgence.setImage(update.getImage());
            oldAgence.setVille(update.getVille());
            return this.agenceRepository.save(oldAgence);
        }).orElseThrow(()->new ObjectNotFoundException("agence",agenceId));
    }
}
