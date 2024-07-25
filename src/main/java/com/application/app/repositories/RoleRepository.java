package com.application.app.repositories;

import com.application.app.models.entities.auth.Role;
import com.application.app.models.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select * from roles r where r.name = :name", nativeQuery = true)
    Optional<Role> findByName(@Param("name") String name);

}
