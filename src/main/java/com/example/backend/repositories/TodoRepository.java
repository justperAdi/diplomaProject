package com.example.backend.repositories;

import com.example.backend.models.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByDeletedAtIsNull();
    List<Todo> findAllByUserIdAndDeletedAtIsNull(Long id);
    Todo findByIdAndDeletedAtIsNull(Long id);
    List<Todo> findAllByCurrentAtAndUserIdAndDeletedAtIsNull(Date currentAt, Long id);
}
