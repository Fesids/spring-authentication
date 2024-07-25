package com.application.app.services;

import com.application.app.models.dtos.PermissionLoadDto;
import com.application.app.models.entities.auth.Permission;
import com.application.app.models.entities.auth.Role;
import com.application.app.models.enums.PermissionLoadMode;
import com.application.app.repositories.PermissionRepository;
import com.application.app.repositories.RoleRepository;
import com.application.app.services.interfaces.PermissionLoader;
import com.google.gson.Gson;
import net.bytebuddy.description.method.MethodDescription;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionLoadImpl implements PermissionLoader {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${app.permission.load.mode")
    private PermissionLoadMode loadMode;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    public PermissionLoadImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    private void addPermissionToRole(Permission permission, String[] rolesNames) {
        Arrays.stream(rolesNames).parallel()
                .forEach(roleName -> {
                    Optional<Role> role = roleRepository.findByName(roleName);

                    role.ifPresent(roleFound-> {
                        if(!roleFound.hasPermission(permission.getName())) {
                            roleFound.addPermission(permission);

                            roleRepository.save(roleFound);
                        }
                    });
                });
    }

    private void loadPermissions(List<PermissionLoadDto> permissionLoadDtoList) {

        permissionLoadDtoList.parallelStream()
                .forEach(
                        permissionLoadDto -> {
                            Permission permissionCreated;
                            Optional<Permission> permission = permissionRepository.findByName(permissionLoadDto.getName());


                            if(permission.isEmpty()) {
                                permissionCreated = permissionRepository.save(permissionLoadDto.toPermission());
                            } else {
                                permissionCreated = permission.get();
                            }

                            addPermissionToRole(permissionCreated, permissionLoadDto.getRoleNames());

                        });

    }

    @Override
    public void load() {
        List<PermissionLoadDto> permissionLoadDtoList;

        if (loadMode.equals(PermissionLoadMode.CREATE)) {
            permissionRepository.deleteAll();
        }

        Resource resource = new ClassPathResource("permission.json");

        try(InputStream inputStream = resource.getInputStream()) {
            byte[] binaryData = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(binaryData, StandardCharsets.UTF_8);

            Type permissionLoadDtoListType = new TypeToken<ArrayList<PermissionLoadDto>>(){

            }.getType();

            permissionLoadDtoList = new Gson().fromJson(data, permissionLoadDtoListType);

            loadPermissions(permissionLoadDtoList);

        } catch (IOException ignored){
            logger.error("Loading permissions: Failed to read permission file!");
        }
    }
}
