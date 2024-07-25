package com.application.app.models.response;

import com.application.app.models.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class UserListResponse {

    private List<User> data;

}
