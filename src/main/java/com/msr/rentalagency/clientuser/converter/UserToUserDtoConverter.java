package com.msr.rentalagency.clientuser.converter;

import com.msr.rentalagency.clientuser.ClientUser;
import com.msr.rentalagency.clientuser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<ClientUser, UserDto> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public UserDto convert(ClientUser source) {
        return  new UserDto(
                source.getId(),
                source.getUsername(),
                source.getRoles(),
                source.isEnabled()
        );
    }
}
