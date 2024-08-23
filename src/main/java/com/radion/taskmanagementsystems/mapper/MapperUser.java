package com.radion.taskmanagementsystems.mapper;

import com.radion.taskmanagementsystems.dto.SignInRequestDto;
import com.radion.taskmanagementsystems.dto.SignUpRequestDto;
import com.radion.taskmanagementsystems.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperUser {

    User fromRegistrationToEntity(SignUpRequestDto signUpRequestDto);
    User fromAuthenticationToEntity(SignInRequestDto signInRequestDto);

}
