package com.msr.rentalagency.clientuser.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(
       Integer id ,
       @NotEmpty(message = "Le nom est obligatoire")
       String username,
       @NotEmpty(message = "Le role est obligatoire")
       String roles,
       Boolean enabled

) {
}
