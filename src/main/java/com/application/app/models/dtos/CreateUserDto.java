package com.application.app.models.dtos;

import com.application.app.constraints.FieldMatch;
import com.application.app.models.entities.auth.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

@ApiModel(value = "RegisterParam", description = "Required params to create role")
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "Both password field must match")
})
@Accessors(chain = true)
@Getter
@Setter
public class CreateUserDto {

    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(notes = "User first name", required = true)
    @NotBlank(message = "The user name is required")
    private String name;

    @ApiModelProperty(notes = "User last name", required = true)
    @NotBlank(message = "The user last name is required")
    private String lastName;

    @ApiModelProperty(notes = "User email address", required = true)
    @Email(message = "Email address is not valid")
    @NotBlank(message = "The user email is required")
    private String email;

    @ApiModelProperty(notes = "User's password (must be at least 6 characters)", required = true)
    @Size(min = 6, message = "Must be at least 6 characters")
    private String password;

    @ApiModelProperty(notes = "Password confirmation", required = true)
    @javax.validation.constraints.NotBlank(message = "This field is required")
    private String confirmPassword;

    @ApiModelProperty(notes = "User activate")
    private boolean active;

    @ApiModelProperty(notes = "Indicates if the will be enabled or not")
    private boolean enabled;

    @ApiModelProperty(notes = "Indicates if has confirmed his account")
    private boolean confirmed;

    @ApiModelProperty(notes = "User role")
    private Role role;

    @ApiModelProperty(notes = "Set")
    private Integer setor;

    private Integer occupation;

    public CreateUserDto(){
        active = true;
    }

}
