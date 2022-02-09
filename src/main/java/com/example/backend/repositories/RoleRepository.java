package com.example.backend.repositories;

import com.example.backend.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByDeletedAtIsNull();
    Role findByIdAndDeletedAtIsNull(Long id);
}
