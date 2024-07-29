package com.application.app.services;

import com.application.app.exceptions.ResourceNotFoundException;
import com.application.app.models.dtos.CreateRoleDto;
import com.application.app.models.entities.auth.Role;
import com.application.app.repositories.RoleRepository;
import com.application.app.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.application.app.utils.Constants.ROLE_NOT_FOUND_MESSAGE;

@Service(value = "roleService")
@Transactional
public class RoleServiceimpl implements RoleService {

   // @Autowired
    private final RoleRepository roleRepository;

    public RoleServiceimpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(CreateRoleDto role) {
        return roleRepository.save(role.toRole());
    }

    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().iterator()
                .forEachRemaining(list::add);

        return list;

    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(String name) throws ResourceNotFoundException {
        Optional<Role> roleOptional = roleRepository.findByName(name);

        if (roleOptional.isEmpty()) {
            throw new ResourceNotFoundException(ROLE_NOT_FOUND_MESSAGE);

        }

        return roleOptional.get();

    }

    @Override
    public Role findById(Long id) throws ResourceNotFoundException {
       Optional<Role> roleOptional = roleRepository.findById(id);

       if (roleOptional.isEmpty()) {
           throw new ResourceNotFoundException(ROLE_NOT_FOUND_MESSAGE);
       }

       return roleOptional.get();

    }

    @Override
    public Role update(Long id, CreateRoleDto createRoleDto) throws ResourceNotFoundException {
        Role roleToUpdate = findById(id);

        roleToUpdate
                .setName(createRoleDto.getName())
                .setDescription(createRoleDto.getDescription());

        return roleRepository.save(roleToUpdate);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }




}
