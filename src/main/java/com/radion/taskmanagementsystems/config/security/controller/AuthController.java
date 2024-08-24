package com.radion.taskmanagementsystems.config.security.controller;

import com.radion.taskmanagementsystems.config.jwt.JwtAuthenticationResponse;
import com.radion.taskmanagementsystems.config.security.service.AuthenticationService;
import com.radion.taskmanagementsystems.dto.SignInRequestDto;
import com.radion.taskmanagementsystems.dto.SignUpRequestDto;
import com.radion.taskmanagementsystems.exception.model.ExceptionModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
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
@Tag(name = "Authentication")
@ApiResponses(@ApiResponse(responseCode = "404",
        content = @Content( schema = @Schema( implementation = ExceptionModel.class))))
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "User registration")
    @PostMapping("/sign-up")
    @SecurityRequirements
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequestDto request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @Operation(summary = "User authorization")
    @PostMapping("/sign-in")
    @SecurityRequirements
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequestDto request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}