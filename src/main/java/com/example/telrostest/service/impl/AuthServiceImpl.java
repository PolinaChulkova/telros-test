package com.example.telrostest.service.impl;

import com.example.telrostest.dto.UserDataDto;
import com.example.telrostest.model.User;
import com.example.telrostest.model.role.Role;
import com.example.telrostest.repository.RoleRepo;
import com.example.telrostest.repository.UserRepo;
import com.example.telrostest.service.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserDataDto registrationRequest) {
        User user = new User(registrationRequest);

        if (userRepo.existsByLogin(registrationRequest.getLogin())) {
            throw new EntityExistsException("Пользователь с login: " + registrationRequest.getLogin() + " уже существует!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = registrationRequest.getRoles().stream()
                .map(r -> roleRepo.findByRoleName(r)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Роль " + r.name() + " не найдена!")))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        return userRepo.save(user);
    }
}
