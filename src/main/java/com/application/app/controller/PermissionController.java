package com.application.app.controller;

import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.entities.auth.Permission;
import com.application.app.models.response.*;
import com.application.app.services.interfaces.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.application.app.utils.Constants.*;

@Api(tags = SWG_PERMISSION_TAG_NAME, description = SWG_PERMISSION_TAG_DESCRIPTION)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/permissions")
public class PermissionController {

    //@Autowired
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation(value = SWG_PERMISSION_LIST_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_PERMISSION_LIST_MESSAGE, response = RoleListResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = BadRequestResponse.class),
    })
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<PermissionListResponse> all() {
        return ResponseEntity.ok(new PermissionListResponse(permissionService.findAll()));
    }

    @ApiOperation(value = SWG_PERMISSION_ITEM_OPERATION, response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SWG_PERMISSION_ITEM_MESSAGE, response = SuccessResponse.class),
            @ApiResponse(code = 401, message = UNAUTHORIZED_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN_MESSAGE, response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = PERMISSION_NOT_FOUND_MESSAGE, response = BadRequestResponse.class),
    })
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> one(@PathVariable Long id)
        throws ResourceNotFoundException {
        Optional<Permission> permission = permissionService.findById(id);

        if (permission.isEmpty()) {
            throw new ResourceNotFoundException(PERMISSION_NOT_FOUND_MESSAGE);
        }

        return ResponseEntity.ok(new PermissionResponse(permission.get()));

    }







}
