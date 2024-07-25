package com.application.app.services;

import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.dtos.CreateUserDto;
import com.application.app.models.dtos.UpdatePasswordDto;
import com.application.app.models.dtos.UpdateUserDto;
import com.application.app.models.entities.user.User;
import com.application.app.repositories.UserRepository;
import com.application.app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

import static com.application.app.utils.Constants.USER_NOT_FOUND_MESSAGE;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User save(CreateUserDto createUserDto) {

        User newUser = new User();

        newUser.setEmail(createUserDto.getEmail())
                .setName(createUserDto.getName())
                .setLastName(createUserDto.getLastName())
                .setPassword(bCryptEncoder.encode(createUserDto.getPassword()))
                .setSetor(createUserDto.getSetor())
                .setActive(createUserDto.isActive())
                .setConfirmed(createUserDto.isConfirmed())
                .setEnabled(createUserDto.isEnabled())
                .setSetor(createUserDto.getSetor())
                .setOccupation(createUserDto.getOccupation())
                .setRole(createUserDto.getRole());
        return userRepository.save(newUser);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new ResourceNotFoundException(USER_NOT_FOUND_MESSAGE);
        }


        return user.get();
    }

    @Override
    public User findById(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new ResourceNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        return user.get();
    }

    @Override
    public User update(Long id, UpdateUserDto updateUserDto) throws ResourceNotFoundException {
        User user = findById(id);

        if(updateUserDto.getName() != null){
            user.setName(updateUserDto.getName());
        }
        if(updateUserDto.getLastName() != null){
            user.setLastName(updateUserDto.getLastName());
        }
        if(updateUserDto.getSetor() != null){
            user.setSetor(updateUserDto.getSetor());
        }
        if(updateUserDto.getOccupation() != null){
            user.setOccupation(updateUserDto.getOccupation());
        }
        if(updateUserDto.getEmail() != null){
            user.setEmail(updateUserDto.getEmail());
        }

        return userRepository.save(user);
    }

    @Override
    public void update(User user) {

        userRepository.save(user);

    }

    @Override
    public User updatePassword(Long id, UpdatePasswordDto updatePasswordDto) throws ResourceNotFoundException {

        User user = findById(id);

        if (bCryptEncoder.matches(updatePasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(bCryptEncoder.encode(updatePasswordDto.getNewPassword()));

            return userRepository.save(user);
        }

        return null;

    }

    @Override
    public void updatePassword(Long id, String newPassword) throws ResourceNotFoundException {

        User user = findById(id);

        user.setPassword(bCryptEncoder.encode(newPassword));

        userRepository.save(user);

    }

    @Override
    public void confirm(Long id) throws ResourceNotFoundException {

        User user = findById(id);
        user.setConfirmed(true);
        userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        User user = userOptional.get();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true, user.isConfirmed(), getAuthority(user)
        );
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        user.allPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        return authorities;
    }
















}
