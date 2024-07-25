package com.application.app.models.dtos;


import com.application.app.constraints.Exists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "ForgotPasswordParam", description = "Parameters required to request a reset a link")
@Exists.List({
        @Exists(property = "email", repository = "UserRepository", message = "This email doesn't exist in db!")
})
@Accessors(chain = true)
@Setter
@Getter
public class ForgotPasswordDto {

    @ApiModelProperty(notes = "The email address to sent the recovery pass link to", required = true)
    @Email(message = "Email address is not valid")
    @NotBlank(message = "The email address is required")
    private String email;

}
