package com.example.telrostest.repository;

import com.example.telrostest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    /**
     * Запрос на получение всех пользователей или их поиск по нескольким параметрам (постранично)
     *
     * @param text     - ключевое слово для поиска
     * @param pageable - интерфейс для пагинации
     * @return страница со списком пользователей
     */
    @Transactional
    @Query(value = "select u from User u where " +
            "lower(u.email) like ('%'||lower(trim(:text))||'%') or " +
            "lower(u.lastname) like ('%'||lower(trim(:text))||'%') or " +
            "lower(u.firstname) like ('%'||lower(trim(:text))||'%') or " +
            "lower(u.surname) like ('%'||lower(trim(:text))||'%') or " +
            "u.telephone like ('%'||lower(trim(:text))||'%')")
    Page<User> findAll(@Param("text") String text, Pageable pageable);
}
