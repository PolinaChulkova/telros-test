package com.example.telrostest.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.TreeMap;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<Map<String, Object>> entityExistsException(EntityExistsException ex, HttpServletRequest request) {
        final Map<String, Object> body = new TreeMap<>();

        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "EntityExistsException");
        body.put("message", ex.getMessage());
        body.put("details", "Измените данные в теле запроса.");
        body.put("path", request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Map<String, Object>> entityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        final Map<String, Object> body = new TreeMap<>();

        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "EntityNotFoundException");
        body.put("message", ex.getMessage());
        body.put("details", "Измените id или имя в запросе.");
        body.put("path", request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FileStorageException.class})
    public ResponseEntity<Map<String, Object>> fileStorageException(FileStorageException ex, HttpServletRequest request) {
        final Map<String, Object> body = new TreeMap<>();

        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        body.put("error", "FileStorageException");
        body.put("message", ex.getMessage());
        body.put("details", "Попробуйте позже.");
        body.put("path", request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PersistenceException.class})
    public ResponseEntity<Map<String, Object>> persistenceException(PersistenceException ex, HttpServletRequest request) {
        final Map<String, Object> body = new TreeMap<>();

        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        body.put("error", "PersistenceException");
        body.put("message", ex.getMessage());
        body.put("details", "Попробуйте позже.");
        body.put("path", request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Map<String, Object>> badCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        final Map<String, Object> body = new TreeMap<>();

        body.put("status", HttpStatus.UNAUTHORIZED);
        body.put("error", "BadCredentialsException");
        body.put("message", ex.getMessage());
        body.put("details", "Неверный логин или пароль");
        body.put("path", request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Map<String, Object>> unknownException(Exception ex, HttpServletRequest request) {
        final Map<String, Object> body = new TreeMap<>();

        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        body.put("message", ex.toString());
        body.put("details", "Попробуйте позже.");
        body.put("path", request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
