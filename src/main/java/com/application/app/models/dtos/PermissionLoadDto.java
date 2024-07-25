package com.application.app.models.dtos;


import com.application.app.models.entities.auth.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionLoadDto {

    private String name;

    private String description;

    private String[] roleNames;

    public Permission toPermission(){
        return new Permission(name, description);
    }

}
