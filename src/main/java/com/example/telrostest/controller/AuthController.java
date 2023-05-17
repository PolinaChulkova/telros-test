package com.example.telrostest.controller;

import com.example.telrostest.dto.JwtResponse;
import com.example.telrostest.dto.LoginRequest;
import com.example.telrostest.dto.UserDataDto;
import com.example.telrostest.model.User;
import com.example.telrostest.service.AuthService;
import com.example.telrostest.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthService registrationService;

    /**
     * Авторизация пользователя
     *
     * @param loginRequest - dto для авторизации с логином и паролем
     * @return JSON представление JwtResponse с токеном, user_id, логином и ролями пользователя
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()));

        String jwt = tokenService.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getId(),
                user.getLogin(),
                user.getRoles()));
    }

    /**
     * Регистрация нового пользователя
     *
     * @param registrationRequest - dto с полями для регистрации
     * @return JSON с детальной информацией о пользователе
     */
    @PostMapping("/register")
    public ResponseEntity<User> registration(@RequestBody UserDataDto registrationRequest) {
        return ResponseEntity.ok(registrationService.register(registrationRequest));
    }

    /**
     * Выход текущего пользователя из аккаунта
     * Удаление всех токенов текущего пользователя из БД и очищение контекста
     *
     * @param request - входящий запрос
     * @return статус OK при успешном завершении
     */
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        tokenService.invalidateAllUserTokens(request);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
