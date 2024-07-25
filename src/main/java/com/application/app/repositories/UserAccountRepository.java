package com.application.app.repositories;

import com.application.app.models.entities.user.User;
import com.application.app.models.entities.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {


    @Query(value = "select * from users_accounts u where u.token = :token", nativeQuery = true)
    Optional<UserAccount> findByToken(@Param("token") String token);

}
