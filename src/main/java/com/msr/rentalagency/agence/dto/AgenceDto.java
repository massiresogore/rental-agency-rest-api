package com.msr.rentalagency.agence.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AgenceDto(
        Integer id,
        @NotEmpty(message = "name is required")
        String nom,
        @NotEmpty(message = "tel is required")
        String tel,
        @NotEmpty(message = "email is required")
        String email,
        @NotEmpty(message = "adresse is required")
        String adresse,
        @NotNull(message = "cp is required")
        int cp,
        @NotEmpty(message = "Ville is required")
        String ville,
        @NotEmpty(message = "image is required")
        String image,
        Integer nomberOfVehicules
){}
