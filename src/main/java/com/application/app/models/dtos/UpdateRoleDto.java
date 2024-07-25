package com.application.app.models.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "UpdateRoleParam", description = "Parameters required to update the roles of asn user")
@Accessors(chain = true)
@Setter
@Getter
public class UpdateRoleDto {

    @ApiModelProperty(notes = "User identifier", required = true)
    @NotBlank(message = "The userId is required")
    private String userId;

    @ApiModelProperty(notes = "Array of roles to give to an usrr", required = true)
    @NotEmpty(message = "The field must have at least one item")
    private String[] roles;

}
