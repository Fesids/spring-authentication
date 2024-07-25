package com.application.app.controller;

import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.dtos.CreateUserDto;
import com.application.app.models.entities.auth.Role;
import com.application.app.models.entities.user.User;
import com.application.app.models.response.BadRequestResponse;
import com.application.app.models.response.InvalidDataResponse;
import com.application.app.models.response.SuccessResponse;
import com.application.app.models.response.UserResponse;
import com.application.app.services.interfaces.RoleService;
import com.application.app.services.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.application.app.utils.Constants.*;

@Api(tags = SWG_ADMIN_TAG_NAME, description = SWG_ADMIN_TAG_DESCRIPTION)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final RoleService roleService;

    private final UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @ApiOperation(value = SWG_ADMIN_CREATE_OPERATION, response = BadRequestResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_ADMIN_CREATE_MESSAGE, response = UserResponse.class),
            @ApiResponse(code = 400, message = SWG_ADMIN_CREATE_ERROR, response = BadRequestResponse.class),
            @ApiResponse(code = 422, message = INVALID_DATA_MESSAGE, response = InvalidDataResponse.class),
    })
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserDto createUserDto)
        throws ResourceNotFoundException
    {
        Role roleUser = roleService.findByName(ROLE_ADMIN);

        createUserDto.setRole(roleUser)
                .setConfirmed(true)
                .setEnabled(true);

        User user = userService.save(createUserDto);

        return ResponseEntity.ok(new UserResponse(user));

    }

    @ApiOperation(value = SWG_ADMIN_DELETE_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = SWG_ADMIN_DELETE_MESSAGE, response = SuccessResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = BadRequestResponse.class),
    })
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
