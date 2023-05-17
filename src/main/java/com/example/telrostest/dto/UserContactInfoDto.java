package com.example.telrostest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * dto с контактной информацией о пользователе
 */
@RequiredArgsConstructor
@Getter
public class UserContactInfoDto {
    private final String lastname;
    private final String firstname;
    private final String surname;
    private final String email;
    private final String telephone;
    private final String avatar;
}

