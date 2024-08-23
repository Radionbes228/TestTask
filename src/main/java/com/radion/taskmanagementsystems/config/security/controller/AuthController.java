package com.radion.taskmanagementsystems.config.security.controller;

import com.radion.taskmanagementsystems.config.jwt.JwtAuthenticationResponse;
import com.radion.taskmanagementsystems.config.security.service.AuthenticationService;
import com.radion.taskmanagementsystems.dto.SignInRequestDto;
import com.radion.taskmanagementsystems.dto.SignUpRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequestDto request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequestDto request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}