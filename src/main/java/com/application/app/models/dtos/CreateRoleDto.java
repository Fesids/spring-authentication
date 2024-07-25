package com.application.app.models.dtos;

import com.application.app.models.entities.auth.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "CreateRoleParam", description = "Required params to create role")
@Accessors(chain = true)
@Setter
@Getter
public class CreateRoleDto {

    @ApiModelProperty(notes = "Role Name", required = true)
    @NotBlank(message = "The name field is required")
    private String name;

    @ApiModelProperty(notes = "Role Description")
    private String description;

    private boolean isDefault;

    public Role toRole() {
        return new Role()
                .setName(this.name)
                .setDescription(this.description)
                .setDefault(this.isDefault);
    }



}
