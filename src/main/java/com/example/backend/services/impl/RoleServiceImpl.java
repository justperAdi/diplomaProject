package com.example.backend.services.impl;

import com.example.backend.exceptions.ServiceException;
import com.example.backend.models.entities.Role;
import com.example.backend.repositories.RoleRepository;
import com.example.backend.services.RoleService;
import com.example.backend.shared.utils.codes.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Long id) throws ServiceException {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("role not found")
                .build());
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAllByDeletedAtIsNull();
    }
    @Override
    public Role update(Role role) throws ServiceException {
        if (Objects.isNull(role.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("role is null")
                    .build();
        }
        return roleRepository.save(role);
    }

    @Override
    public Role save(Role role) throws ServiceException {
        if (Objects.nonNull(role.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("role already exists")
                    .build();
        }
        return roleRepository.save(role);
    }

    @Override
    public void delete(Role role) throws ServiceException {
        if (Objects.isNull(role.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("role is null")
                    .build();
        }
        role = findById(role.getId());
        role.setDeletedAt(new Date());
        roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if (Objects.isNull(id)) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Role role = findById(id);
        role.setDeletedAt(new Date());
        roleRepository.save(role);
    }
}
