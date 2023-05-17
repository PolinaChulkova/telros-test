package com.example.telrostest.service;

import com.example.telrostest.dto.UserDataDto;
import com.example.telrostest.model.User;

public interface AuthService {

    User register(UserDataDto registrationRequest);
}
