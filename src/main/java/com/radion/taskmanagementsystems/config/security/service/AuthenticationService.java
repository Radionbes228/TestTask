package com.radion.taskmanagementsystems.config.security.service;

import com.radion.taskmanagementsystems.config.jwt.JwtAuthenticationResponse;
import com.radion.taskmanagementsystems.config.jwt.JwtService;
import com.radion.taskmanagementsystems.config.security.CustomUserDetails;
import com.radion.taskmanagementsystems.dto.SignInRequestDto;
import com.radion.taskmanagementsystems.dto.SignUpRequestDto;
import com.radion.taskmanagementsystems.mapper.impl.MapperUserImpl;
import com.radion.taskmanagementsystems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MapperUserImpl mapperUser;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequestDto request) {

        var user = userService.create(mapperUser.fromRegistrationToEntity(request));

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequestDto request) {
        CustomUserDetails user = (CustomUserDetails) userService
                .loadUserByUsername(request.getEmail());


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
