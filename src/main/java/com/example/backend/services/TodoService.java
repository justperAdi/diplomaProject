package com.example.backend.services;

import com.example.backend.exceptions.ServiceException;
import com.example.backend.models.entities.Todo;

import java.util.Date;
import java.util.List;

public interface TodoService {

    List<Todo> findAll();
    List<Todo> findAllByCurrentAt(Date currentAt, Long id)throws ServiceException;
    List<Todo> findAllByUserId(Long id) throws ServiceException;
    Todo findById(Long id) throws ServiceException;
    Todo update(Todo todo) throws ServiceException;
    Todo save(Todo todo) throws ServiceException;
    void delete(Todo todo) throws ServiceException;
    void deleteById(Long id) throws ServiceException;
}
