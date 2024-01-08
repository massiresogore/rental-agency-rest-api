package com.msr.rentalagency.agence.converter;

import com.msr.rentalagency.agence.Agence;
import com.msr.rentalagency.agence.dto.AgenceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AgenceToAgenceDtoConverter implements Converter<Agence, AgenceDto> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public AgenceDto convert(Agence source) {
     return  new AgenceDto(
             source.getId(),
             source.getNom(),
             source.getTel(),
             source.getEmail(),
             source.getAdresse(),
             source.getCp(),
             source.getVille(),
             source.getImage(),
             source.getNomberOfVehicule()
     );

    }
}
