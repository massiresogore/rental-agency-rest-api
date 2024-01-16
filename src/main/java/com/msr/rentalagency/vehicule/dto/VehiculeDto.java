package com.msr.rentalagency.vehicule.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record VehiculeDto(
        Integer id,
       @NotEmpty(message = "la marque est obligatoire") String  marque,
       @NotEmpty(message = "Le modèle est obligatoire") String  modele,
       @Min(value = 1,message = "Le prix ne peut pas être null")
        Double  prix_journalier,
        @NotEmpty(message = "la description est obligatoire") String  description,
       @NotEmpty(message = "L'image est obligatoire") String  image,
       @Min(value = 1,message = "L'agence ne peut pas etre null") Integer agenceId

) {
}
