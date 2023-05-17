package com.example.telrostest.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

public interface TokenService {
    @Transactional
    String generateJwtToken(Authentication authentication);

    @Transactional
    void invalidateAllUserTokens(HttpServletRequest request);

    boolean validateJwt(String jwt);

    String parseJwt(HttpServletRequest request);

    String getUsernameFromJwt(String jwt);
}
