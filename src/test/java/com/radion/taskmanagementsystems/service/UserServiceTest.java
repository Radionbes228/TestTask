package com.radion.taskmanagementsystems.service;

import com.radion.taskmanagementsystems.entity.User;
import com.radion.taskmanagementsystems.exception.UserNotFoundException;
import com.radion.taskmanagementsystems.repository.UserJpaRepository;
import com.radion.taskmanagementsystems.config.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserJpaRepository userRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UserService userService;

    private User user;
    private CustomUserDetails customUserDetails;
    private final Long userId = 1L;
    private final String username = "test@example.com";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(userId);
        user.setEmail(username);
        customUserDetails = new CustomUserDetails(user);
    }

    @Test
    void testFindById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertEquals(user, result);
    }

    @Test
    void testFindById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage("UserNotFoundExceptionSecurity", null, Locale.getDefault()))
                .thenReturn("User with that id was not found");

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
                () -> userService.findById(userId));

        assertEquals("User with that id was not found", thrown.getMessage());
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(username);

        assertEquals(customUserDetails, result);
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
                () -> userService.loadUserByUsername(username));

        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    void testCreate_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        CustomUserDetails result = userService.create(user);

        assertEquals(customUserDetails, result);
    }
}
