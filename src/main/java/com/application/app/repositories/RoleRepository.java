package com.application.app.repositories;

import com.application.app.models.entities.auth.Permission;
import com.application.app.models.entities.auth.Role;
import com.application.app.models.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select * from roles p where p.name = :name", nativeQuery = true)
    Optional<Role> findByName(@Param("name") String name);
}