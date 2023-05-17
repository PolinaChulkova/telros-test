package com.example.telrostest.dto;

import com.example.telrostest.model.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class JwtResponse {

    private final String token;
    private final String type = "Bearer";
    private final Long id;
    private final String login;
    private final Set<Role> role;

}