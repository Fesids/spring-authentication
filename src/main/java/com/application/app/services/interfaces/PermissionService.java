package com.application.app.services.interfaces;

import com.application.app.models.entities.auth.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    List<Permission> findAll();

    Optional<Permission> findById(Long id);

    Optional<Permission> findByName(String name);
}
