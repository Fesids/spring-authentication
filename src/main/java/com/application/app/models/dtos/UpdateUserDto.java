package com.application.app.models.dtos;


import com.application.app.models.entities.auth.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@ApiModel(value = "UpdateUserParam", description = "Params required to update a user")
@Accessors(chain = true)
@Setter
@Getter
public class UpdateUserDto{

    @ApiModelProperty(notes = "User name")
    private String name;

    @ApiModelProperty(notes = "User last name")
    private String lastName;

    @ApiModelProperty(notes = "User email")
    private String email;

    @ApiModelProperty(notes = "Indicates if a user is active or not")
    private boolean active;

    @ApiModelProperty(notes = "Indicates if the will be enabled or not")
    private boolean enabled;

    @ApiModelProperty(notes = "Indicates if has confirmed his account")
    private boolean confirmed;

    private Set<Role> roles;

    private Integer setor;

    private Integer occupation;


}
