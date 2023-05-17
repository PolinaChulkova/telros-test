package com.example.telrostest.repository;

import com.example.telrostest.model.Token;
import com.example.telrostest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    boolean existsByToken(String token);
    void deleteByUser(User user);
}
