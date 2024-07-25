package com.application.app.models.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "UpdateUserPermissionParam", description = "Params required to update User permissions")
@Accessors(chain = true)
@Setter
@Getter
public class UpdateUserPermissionDto {

    @ApiModelProperty(notes = "Array of permissions to give or remove to a user")
    @NotEmpty(message = "The field must have at least one permission setted")
    private String[] permissions;

}
