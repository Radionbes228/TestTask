package com.radion.taskmanagementsystems.service;


import com.radion.taskmanagementsystems.config.security.CustomUserDetails;
import com.radion.taskmanagementsystems.entity.User;
import com.radion.taskmanagementsystems.exception.UserNotFoundException;
import com.radion.taskmanagementsystems.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserJpaRepository userRepository;
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("{UserNotFoundExceptionSecurity}"));
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return userRepository.findByEmail(username).map(CustomUserDetails::new)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

    }

    public CustomUserDetails create(User user){
        return new CustomUserDetails(userRepository.save(user));
    }

}
