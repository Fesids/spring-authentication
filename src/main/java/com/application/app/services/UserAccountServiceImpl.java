package com.application.app.services;

import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.dtos.CreateUserDto;
import com.application.app.models.dtos.UpdatePasswordDto;
import com.application.app.models.dtos.UpdateUserDto;
import com.application.app.models.entities.user.User;
import com.application.app.models.entities.user.UserAccount;
import com.application.app.repositories.UserAccountRepository;
import com.application.app.services.interfaces.UserAccountService;
import com.application.app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.application.app.utils.Constants.INVALID_TOKEN_MESSAGE;
import static com.application.app.utils.Constants.RESOURCE_NOT_FOUND_MESSAGE;

@Service(value = "UserAccountService")
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    //@Autowired
    private final UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository){
        this.userAccountRepository = userAccountRepository;
    }


    @Override
    public UserAccount save(User user, String token) {
        UserAccount newUserAccount = new UserAccount();
        Date dateNow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dateNow);
        c.add(Calendar.DATE, 2);

        newUserAccount.setUser(user)
                .setToken(token)
                .setExpireAt(c.getTime().getTime());

        return userAccountRepository.save(newUserAccount);
    }

    @Override
    public List<UserAccount> findAll() {
        List<UserAccount> list = new ArrayList<>();
        userAccountRepository.findAll().iterator()
                .forEachRemaining(list::add);

        return list;
    }

    @Override
    public void delete(Long id) {

        userAccountRepository.deleteById(id);

    }

    @Override
    public UserAccount findByToken(String token) throws ResourceNotFoundException {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findByToken(token);

        if(userAccountOptional.isEmpty()){
            throw new ResourceNotFoundException(INVALID_TOKEN_MESSAGE);
        }

        return userAccountOptional.get();
    }

    @Override
    public UserAccount findById(Long id) throws ResourceNotFoundException {
        Optional<UserAccount> confirmAccountOptional = userAccountRepository.findById(id);

        if (confirmAccountOptional.isEmpty()) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE);
        }

        return confirmAccountOptional.get();
    }
}
