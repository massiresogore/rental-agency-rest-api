package com.msr.rentalagency.vehicule.converter;

import com.msr.rentalagency.vehicule.Vehicule;
import com.msr.rentalagency.vehicule.dto.VehiculeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VehiculeToVehiculeDtoConverter implements Converter<Vehicule, VehiculeDto> {


    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public VehiculeDto convert(Vehicule source) {

        return new VehiculeDto(
                source.getId(),
                source.getMarque(),
                source.getModele(),
                source.getPrix_journalier(),
                source.getDescription(),
                source.getImage(),
                source.getAgence().getId()
        );
    }
}
