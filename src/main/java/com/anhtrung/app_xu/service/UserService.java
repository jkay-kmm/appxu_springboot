package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.Role;
import com.anhtrung.app_xu.domain.User;
import com.anhtrung.app_xu.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Set;


@Service @RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository users;
    private final PasswordEncoder encoder;


    public User createUser(String email, String rawPassword, String fullName) {
        var user = User.builder()
                .email(email)
                .password(encoder.encode(rawPassword))
                .fullName(fullName)
                .roles(Set.of(Role.USER))
                .enabled(true)
                .build();
        return users.save(user);
    }


    public User getById(Long id) { return users.findById(id).orElseThrow(); }


    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var u = users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPassword())
                .roles(u.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .accountLocked(!u.isEnabled())
                .build();
    }
}
