package com.example.telrostest.dto;

import com.example.telrostest.model.role.ERole;
import com.example.telrostest.model.role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * dto для создания нового пользователя
 */
@RequiredArgsConstructor
@Getter
public class UserDataDto {

    private final String login;
    private final String password;

    private final String email;
    private final String lastname;
    private final String firstname;
    private final String surname;
    private final String telephone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date date_birth;
    @Enumerated(EnumType.STRING)
    private final Set<ERole> roles;

    public Set<Role> convertToRoleSet() {
        return roles.stream().map(r -> new Role(r)).collect(Collectors.toSet());
    }
}