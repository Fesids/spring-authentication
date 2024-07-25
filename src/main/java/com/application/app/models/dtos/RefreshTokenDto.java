package com.application.app.models.dtos;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "RefreshTokenParams", description = "Parameters required to create or update refresh User")
@Accessors(chain = true)
@Setter
@Getter
public class RefreshTokenDto {

    @ApiModelProperty(notes = "Refresh token to used to validate the user and generate a new token", required = true)
    @NotBlank(message = "The token is required")
    private String token;

}
