package com.radion.taskmanagementsystems.mapper.impl;

import com.radion.taskmanagementsystems.dto.SignInRequestDto;
import com.radion.taskmanagementsystems.dto.SignUpRequestDto;
import com.radion.taskmanagementsystems.entity.User;
import com.radion.taskmanagementsystems.mapper.MapperUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperUserImpl implements MapperUser {
    private final PasswordEncoder passwordEncoder;
    @Override
    public User fromRegistrationToEntity(SignUpRequestDto signUpRequestDto) {
        return User.builder()
                .firstName(signUpRequestDto.getFirstName())
                .name(signUpRequestDto.getName())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build();
    }

    @Override
    public User fromAuthenticationToEntity(SignInRequestDto signInRequestDto) {
        return User.builder()
                .email(signInRequestDto.getEmail())
                .password(signInRequestDto.getPassword())
                .build();
    }
}
