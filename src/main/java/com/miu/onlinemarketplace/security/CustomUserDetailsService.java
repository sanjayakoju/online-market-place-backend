package com.miu.onlinemarketplace.security;

import com.miu.onlinemarketplace.entities.Role;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.security.models.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = user.getRole();
        return new CustomUserDetails(user.getUserId(), user.getEmail(), user.getPassword(), role.getRole());
    }
}