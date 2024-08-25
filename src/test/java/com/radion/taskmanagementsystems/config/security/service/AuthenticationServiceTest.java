package com.radion.taskmanagementsystems.config.security.service;

import com.radion.taskmanagementsystems.config.jwt.JwtAuthenticationResponse;
import com.radion.taskmanagementsystems.config.jwt.JwtService;
import com.radion.taskmanagementsystems.config.security.CustomUserDetails;
import com.radion.taskmanagementsystems.dto.SignInRequestDto;
import com.radion.taskmanagementsystems.dto.SignUpRequestDto;
import com.radion.taskmanagementsystems.entity.User;
import com.radion.taskmanagementsystems.mapper.impl.MapperUserImpl;
import com.radion.taskmanagementsystems.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.*;

public class AuthenticationServiceTest {

    @Mock
    private MapperUserImpl mapperUser;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private User user;

    @BeforeEach
    void setUp(){
        openMocks(this);

        user = User.builder()
                .name("Name")
                .email("test@mail.ru")
                .password("111111111111111111111111")
                .firstName("First name")
                .build();
    }

    @Test
    void signUp_ShouldReturnJwtAuthenticationResponse() {
        SignUpRequestDto requestDto = new SignUpRequestDto();

        CustomUserDetails userDetails = new CustomUserDetails(user);
        when(mapperUser.fromRegistrationToEntity(requestDto)).thenReturn(userDetails.getUser());
        when(userService.create(userDetails.getUser())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

        JwtAuthenticationResponse response = authenticationService.signUp(requestDto);

        assertEquals("jwt-token", response.getToken());
    }


    @Test
    void signIn_ShouldReturnJwtAuthenticationResponse() {
        SignInRequestDto requestDto = new SignInRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password");

        CustomUserDetails userDetails = new CustomUserDetails(user);
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, "password"));

        JwtAuthenticationResponse response = authenticationService.signIn(requestDto);

        assertEquals("jwt-token", response.getToken());
    }
}
