package com.msr.rentalagency.vehicule.converter;

import com.msr.rentalagency.agence.AgenceService;
import com.msr.rentalagency.vehicule.Vehicule;
import com.msr.rentalagency.vehicule.dto.VehiculeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VehiculeDtoToVehiculeConverter implements Converter<VehiculeDto, Vehicule> {

        private final AgenceService agenceService;

    public VehiculeDtoToVehiculeConverter(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Vehicule convert(VehiculeDto source) {
        Vehicule vehicule = new Vehicule();
        vehicule.setId(source.id());
        vehicule.setModele(source.modele());
        vehicule.setMarque(source.marque());
        vehicule.setDescription(source.description());
        vehicule.setImage(source.image());
        vehicule.setPrix_journalier(source.prix_journalier());
        vehicule.setAgence(this.agenceService.findById(source.agenceId()));

        return vehicule;
    }
}
