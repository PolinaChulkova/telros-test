package com.example.telrostest.service;

import com.example.telrostest.dto.UserContactInfoDto;
import com.example.telrostest.dto.UserUpdateDto;
import com.example.telrostest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService extends UserDetailsService {

    User findUserByLogin(String login);

    User findUserById(long id);

    Page<UserContactInfoDto> getUsers(String text, Pageable pageable);

    User saveUser(User user);

    User updateUser(String login, UserUpdateDto userUpdateDto);

    void deleteUserByLogin(String login);

    void loadAvatar(String login, String filename);
}
