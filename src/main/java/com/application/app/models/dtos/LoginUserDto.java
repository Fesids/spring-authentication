package com.application.app.models.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "LoginUserParam", description = "Param required to login user")
@Accessors(chain = true)
@Setter
@Getter
public class LoginUserDto {

    @ApiModelProperty(notes = "User email addresss", required = true)
    @Email(message = "Email address is not valid")
    @NotBlank(message = "The email is required")
    private String email;

    @ApiModelProperty(notes = "User password (Min character 6)", required = true)
    @Size(min = 6, message = "Must be at least 6 characters")
    private String password;


}
