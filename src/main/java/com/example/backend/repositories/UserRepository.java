package com.example.backend.repositories;

import com.example.backend.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeletedAtIsNull();
    User findByLoginAndDeletedAtIsNull(String login);
    User findByIdAndDeletedAtIsNull(Long id);
}
