package com.example.backend.services.impl;

import com.example.backend.exceptions.ServiceException;
import com.example.backend.models.entities.Todo;
import com.example.backend.repositories.TodoRepository;
import com.example.backend.services.TodoService;
import com.example.backend.shared.utils.codes.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo findById(Long id) throws ServiceException {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        return todoOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("todo not found")
                .build());
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Todo update(Todo todo) throws ServiceException {
        if (Objects.isNull(todo.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("role is null")
                    .build();
        }
        return todoRepository.save(todo);
    }

    @Override
    public Todo save(Todo todo) throws ServiceException {
        if (Objects.nonNull(todo.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("role already exists")
                    .build();
        }
        todo.setDone(0);
        return todoRepository.save(todo);
    }

    @Override
    public void delete(Todo todo) throws ServiceException {
        if (Objects.isNull(todo.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("todo is null")
                    .build();
        }
        todo = findById(todo.getId());
        todo.setDeletedAt(new Date());
        todoRepository.save(todo);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if (Objects.isNull(id)) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Todo todo = findById(id);
        todo.setDeletedAt(new Date());
        todoRepository.save(todo);
    }

    @Override
    public List<Todo> findAllByCurrentAt(Date currentAt, Long id) {
        if (currentAt == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("date is null")
                    .build();
        }
        if (id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        return todoRepository.findAllByCurrentAtAndUserIdAndDeletedAtIsNull(currentAt , id);
    }

    @Override
    public List<Todo> findAllByUserId(Long id) throws ServiceException {
        if (id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        return todoRepository.findAllByUserIdAndDeletedAtIsNull(id);
    }
}
