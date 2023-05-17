package com.example.telrostest.service.impl;

import com.example.telrostest.model.Token;
import com.example.telrostest.model.User;
import com.example.telrostest.repository.TokenRepo;
import com.example.telrostest.service.TokenService;
import com.example.telrostest.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${app.jwtSecret}")
    private String secret;

    @Getter
    @Value("${app.jwtExpirationMs}")
    private int jwtExpiration;

    private final TokenRepo tokenRepo;
    private final UserService userService;

    /**
     * Создание и сохранение нового токена
     *
     * @param authentication - информация для авторизации пользователя
     * @return новый токен
     */
    @Override
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        String token = Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        tokenRepo.save(new Token(token, userService.findUserByLogin(userPrincipal.getUsername())));

        return token;
    }

    /**
     * Удаление токена из БД
     */
    @Override
    public void invalidateAllUserTokens(HttpServletRequest request) {
        User user = userService.findUserByLogin(request.getUserPrincipal().getName());
        tokenRepo.deleteByUser(user);
    }

    @Override
    public boolean validateJwt(String jwt) {
        if (tokenRepo.existsByToken(jwt) && Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt) != null) {
            return true;
        } else throw new ExpiredJwtException(Jwts.header(), Jwts.claims(), "Токен не действителен!");
    }

    @Override
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

    @Override
    public String getUsernameFromJwt(String jwt) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
    }
}
