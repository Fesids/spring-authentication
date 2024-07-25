package com.application.app.services.interfaces;

import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.dtos.CreateRoleDto;
import com.application.app.models.entities.auth.Role;

import java.util.List;

public interface RoleService {

    Role save(CreateRoleDto role);

    List<Role> findAll();

    void delete(Long id);

    Role findByName(String name) throws ResourceNotFoundException;

    Role findById(Long id) throws ResourceNotFoundException;

    Role update(Long id, CreateRoleDto createRoleDto) throws ResourceNotFoundException;

    Role update(Role role);

}
