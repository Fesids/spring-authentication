package com.application.app.models.entities.user;


import com.application.app.models.entities.BaseModel;
import com.application.app.models.entities.auth.Permission;
import com.application.app.models.entities.auth.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Getter
@Setter
@Entity
@Table(name = "Users")
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String lastName;

    private String email;

    private String password;

    private boolean active;

    private boolean enabled;

    private boolean confirmed;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;
    //private Object role;

    private Integer setor;

    private Integer occupation;

    @JoinTable(
            name = "users_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
   // private Set<Object> permissions;
    public User(){permissions = new HashSet<>();}


    public User(
            String name,
            String lastName,
            String email,
            String password,

            Integer setor,
            Integer occupation
    ){
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.active = true;
        this.enabled = true;
        this.confirmed = false;
        this.setor = setor;
        this.occupation = occupation;
        permissions = new HashSet<>();
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    public boolean hasPermission(String permissionName){
        Optional<Permission> permissionItem = this.permissions.stream()
                .filter(permission -> permission.getName().equals(permissionName))
                .findFirst();

        return permissionItem.isPresent();


    }

    public void removePermission(Permission permission) {
        Stream<Permission> newPermissions = this.permissions.stream()
                .filter(permission1 -> permission1.getName().equals(permission.getName()));

        this.permissions = newPermissions.collect(Collectors.toSet());
    }

    public  Set<Permission> allPermissions() {
        Set<Permission> userPermissions = this.permissions;
        Set<Permission> userRolePermissions = this.role.getPermissions();

        Set<Permission> all = new HashSet<>(userPermissions);
        all.addAll(userRolePermissions);



        return all;
    }





}
