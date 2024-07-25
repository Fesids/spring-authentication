package com.application.app.models.response;

import com.application.app.models.entities.auth.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PermissionResponse {

    private Permission data;

}
