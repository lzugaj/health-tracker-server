package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> searchedUser = userRepository.findByEmail(email);
        if (searchedUser.isEmpty()) {
            throw new EntityNotFoundException(
                    String.format("Cannot find searched User by given email. [email=%s]", email)
            );
        }

        return searchedUser.get();
    }
}
