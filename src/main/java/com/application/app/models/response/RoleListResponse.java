package com.application.app.models.response;

import com.application.app.models.entities.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class RoleListResponse {

    private List<Role> data;

}
