package com.application.app.bootstrap;


import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.dtos.CreateRoleDto;
import com.application.app.models.dtos.CreateUserDto;
import com.application.app.services.interfaces.PermissionLoader;
import com.application.app.services.interfaces.RoleService;
import com.application.app.services.interfaces.UserService;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.application.app.utils.Constants.*;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleService roleService;

    private final UserService userService;

    private final PermissionLoader permissionLoader;


    public DataSeeder(RoleService roleService, UserService userService, PermissionLoader permissionLoader) {
        this.roleService = roleService;
        this.userService = userService;
        this.permissionLoader = permissionLoader;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadRoles();

        permissionLoader.load();

        loadUsers();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }


    public void loadRoles() {
        Map<String, String> rolesMap = new HashMap<>();
        rolesMap.put(ROLE_USER, "User role");
        rolesMap.put(ROLE_ADMIN, "Admin role");
        rolesMap.put(ROLE_SUPER_ADMIN, "Super admin role");

        rolesMap.forEach((key, value) -> {
            try{
                roleService.findByName(key);
            } catch (ResourceNotFoundException e) {
                CreateRoleDto createRoleDto = new CreateRoleDto();

                createRoleDto.setName(key)
                        .setDescription(value)
                        .setDefault(true);

                roleService.save(createRoleDto);
            }
        });

    }

    private void loadUsers() throws ResourceNotFoundException {
        CreateUserDto superAdmin = new CreateUserDto()
                .setEmail("admin@gmail.com")
                .setName("Admin")
                .setLastName("")
                .setConfirmed(true)
                .setEnabled(true)
                .setActive(true)
                .setOccupation(null)
                .setSetor(null)
                .setPassword("67890000");

        try{
            userService.findByEmail(superAdmin.getEmail());
        } catch (ResourceNotFoundException e) {
            superAdmin.setRole(roleService.findByName(ROLE_SUPER_ADMIN));

            userService.save(superAdmin);
        }

    }

}
