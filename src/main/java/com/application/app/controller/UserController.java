package com.application.app.controller;


import com.application.app.exceptions.PasswordNotMatchException;
import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.dtos.UpdatePasswordDto;
import com.application.app.models.dtos.UpdateUserDto;
import com.application.app.models.dtos.UpdateUserPermissionDto;
import com.application.app.models.entities.auth.Permission;
import com.application.app.models.entities.user.User;
import com.application.app.models.response.*;
import com.application.app.services.interfaces.PermissionService;
import com.application.app.services.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

import static com.application.app.utils.Constants.*;

@Api(tags = SWG_USER_TAG_NAME, description = SWG_USER_TAG_DESCRIPTION)
@RestController
@RequestMapping(value = "/users")
@Validated
public class UserController {

    private final Log logger = LogFactory.getLog(this.getClass());

    private final UserService userService;

    private final PermissionService permissionService;

    public UserController(UserService userService, PermissionService permissionService){
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @ApiOperation(value = SWG_USER_LIST_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_USER_DELETE_MESSAGE, response = UserListResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = INVALID_DATA_MESSAGE, response = BadRequestResponse.class)
    })
    @PreAuthorize("hasAuthority('read:users')")
    @GetMapping
    public ResponseEntity<UserListResponse> all() {
        return ResponseEntity.ok(new UserListResponse(userService.findAll()));
    }

    @ApiOperation(value = SWG_USER_LOGGED_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_USER_LOGGED_MESSAGE, response = UserResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class)

    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> currentUser() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(new UserResponse(userService.findByEmail(authentication.getName())));
    }


    @ApiOperation(value = SWG_USER_ITEM_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_USER_ITEM_MESSAGE, response = UserResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = BadRequestResponse.class)
    })
    @PreAuthorize("hasAuthority('read:user')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> one(@PathVariable Long id)
    throws ResourceNotFoundException {
        return ResponseEntity.ok(new UserResponse(userService.findById(id)));
    }

    @ApiOperation(value = SWG_USER_UPDATE_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_USER_UPDATE_MESSAGE, response = UserResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 422, message = INVALID_DATA_MESSAGE, response = BadRequestResponse.class),
    })
    @PreAuthorize("hasAuthority('update:user')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto)
        throws ResourceNotFoundException
    {
        return ResponseEntity.ok(new UserResponse(userService.update(id, updateUserDto)));
    }


    @ApiOperation(value = SWG_USER_UPDATE_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_USER_UPDATE_PWD_MESSAGE, response = UserResponse.class),
            @ApiResponse(code = 400, message = SWG_USER_UPDATE_PWD_ERROR, response = BadRequestResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 422, message = INVALID_DATA_MESSAGE, response = InvalidDataResponse.class),

    })
    @PreAuthorize("hasAuthority('change:password')")
    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponse> updatePassword(
            @PathVariable Long id, @Valid @RequestBody UpdatePasswordDto updatePasswordDto
            ) throws PasswordNotMatchException, ResourceNotFoundException{
        User user = userService.updatePassword(id, updatePasswordDto);

        if (user == null) {
            throw new PasswordNotMatchException(PASSWORD_NOT_MATCH_MESSAGE);
        }

        return ResponseEntity.ok(new UserResponse(user));
    }


    @ApiOperation(value = SWG_USER_DELETE_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = SWG_USER_DELETE_MESSAGE, response = SuccessResponse.class),

            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = SuccessResponse.class),
    })
    @PreAuthorize("hasAuthority('delete:user')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = SWG_USER_PERMISSION_ASSIGN_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_USER_PERMISSION_ASSIGN_MESSAGE, response = UserResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 422, message = INVALID_DATA_MESSAGE, response = InvalidDataResponse.class),
    })
    @PreAuthorize("hasAuthority('assign:permission')")
    @PutMapping("/{id}/permissions")
    public ResponseEntity<UserResponse> assignPermissions(@PathVariable Long id, @Valid @RequestBody UpdateUserPermissionDto updateUserPermissionDto)
        throws ResourceNotFoundException
    {
        User user = userService.findById(id);

        Arrays.stream(updateUserPermissionDto.getPermissions()).forEach(permissionName -> {
            Optional<Permission> permission = permissionService.findByName(permissionName);

            if(permission.isPresent() && !user.hasPermission(permissionName)) {
                user.addPermission(permission.get());
            }
        });

        userService.update(user);

        return ResponseEntity.ok().body(new UserResponse(user));
    }



    @ApiOperation(value = SWG_USER_PERMISSION_REVOKE_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_USER_PERMISSION_REVOKE_MESSAGE, response = UserResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 422, message = INVALID_DATA_MESSAGE, response = InvalidDataResponse.class),
    })
    @PreAuthorize("hasAuthority('revoke:permission')")
    @DeleteMapping("/{id}/permissions")
    public ResponseEntity<User> revokePermissions(@PathVariable Long id, @Valid @RequestBody UpdateUserPermissionDto updateUserPermissionDto)
        throws ResourceNotFoundException
    {
        User user = userService.findById(id);

        Arrays.stream(updateUserPermissionDto.getPermissions()).forEach(permissionName -> {
            Optional<Permission> permission = permissionService.findByName(permissionName);

            if(permission.isPresent() && user.hasPermission(permissionName)){
                user.removePermission(permission.get());
            }
        });

        userService.update(user);

        return ResponseEntity.ok().body(user);

    }

}
