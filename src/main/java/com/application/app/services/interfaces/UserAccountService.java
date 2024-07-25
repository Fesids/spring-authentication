package com.application.app.services.interfaces;

import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.entities.user.User;
import com.application.app.models.entities.user.UserAccount;

import java.util.List;

public interface UserAccountService {

    UserAccount save(User user, String token);

    List<UserAccount> findAll();

    void delete(Long id);

    UserAccount findByToken(String token) throws ResourceNotFoundException;

    UserAccount findById(Long id) throws ResourceNotFoundException;

}
