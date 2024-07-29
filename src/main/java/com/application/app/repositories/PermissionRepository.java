package com.application.app.repositories;

import com.application.app.models.entities.auth.Permission;
import com.application.app.models.entities.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "select * from permissions p where p.name = :name", nativeQuery = true)
    Optional<Permission> findByName(@Param("name") String name);

}
