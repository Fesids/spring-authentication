package com.application.app.services.interfaces;

import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.dtos.CreateUserDto;
import com.application.app.models.dtos.UpdatePasswordDto;
import com.application.app.models.dtos.UpdateUserDto;
import com.application.app.models.entities.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User save(CreateUserDto createUserDto);

    List<User> findAll();

    void delete(Long id);

    User findByEmail(String email) throws ResourceNotFoundException;

    User findById(Long id) throws ResourceNotFoundException;

    User update(Long id, UpdateUserDto updateUserDto) throws ResourceNotFoundException;

    void update(User user);

    User updatePassword(Long id, UpdatePasswordDto updatePasswordDto) throws ResourceNotFoundException;

    void updatePassword(Long id, String newPassword) throws ResourceNotFoundException;

    void confirm(Long id) throws ResourceNotFoundException;

}
