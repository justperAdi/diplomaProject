package com.example.backend.services;

import com.example.backend.exceptions.ServiceException;
import com.example.backend.models.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    User findById(Long id) throws ServiceException;
    User update(User user) throws ServiceException;
    User save(User user) throws ServiceException;
    void delete(User user) throws ServiceException;
    void deleteById(Long id) throws ServiceException;
    Set getAuthority(User user);
    User findByLogin(String login);
    User getUserByAuthentication(Authentication authentication);
}
