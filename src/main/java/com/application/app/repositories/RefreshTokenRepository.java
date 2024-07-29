package com.application.app.repositories;

import com.application.app.models.entities.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByValue(String value);

}
