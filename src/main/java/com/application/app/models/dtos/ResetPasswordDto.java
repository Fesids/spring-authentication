package com.application.app.models.dtos;

import com.application.app.constraints.FieldMatch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "ResetPasswordParam", description = "Parameters required to reset password")
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password field must match")

})
@Accessors(chain = true)
@Setter
@Getter
public class ResetPasswordDto {

    @ApiModelProperty(notes = "The token included in the reset link", required = true)
    @NotBlank(message = "The token is required")
    private String token;

    @ApiModelProperty(notes = "New value of password", required = true)
    @Size(min = 6, message = "Must be at least 6 characters")
    @NotBlank(message = "This field is required")
    private String password;

    @ApiModelProperty(notes = "Confirmation of the new value of the password", required = true)
    @NotBlank(message = "This field is required")
    private String confirmPassword;


}
