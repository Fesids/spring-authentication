package com.application.app.services;

import com.application.app.models.entities.auth.Permission;
import com.application.app.repositories.PermissionRepository;
import com.application.app.services.interfaces.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    //@Autowired
    private PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }


    @Override
    public List<Permission> findAll() {
        List<Permission> list = new ArrayList<>();
        permissionRepository.findAll().iterator()
                .forEachRemaining(list::add);

        return list;
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return permissionRepository.findByName(name);
    }
}
