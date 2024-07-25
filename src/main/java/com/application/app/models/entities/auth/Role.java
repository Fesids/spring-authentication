package com.application.app.models.entities.auth;

import com.application.app.models.entities.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Entity
@Table(name = "roles")
public class Role extends BaseModel {

    private String name;

    private String description;

    private boolean isDefault;

    private Set<Permission> permissions;

    public Role(){permissions = new HashSet<>();}

    public Role addPermission(Permission permission){
        this.permissions.add(permission);

        return this;
    }

    public boolean hasPermission(String permissionName){
        Optional<Permission> permissionItem = this.permissions.stream()
                .filter(permission -> permission.getName().equals(permissionName))
                .findFirst();
        return permissionItem.isPresent();
    }

    public Role removePermission(Permission permission) {
        Stream<Permission> newPermissions = this.permissions.stream()
                .filter(permission1 -> !permission1.getName().equals(permission.getName()));

        this.permissions = newPermissions.collect(Collectors.toSet());

        return this;
    }







}
