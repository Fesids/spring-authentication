package com.application.app.models.entities.auth;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;

//@RedisHash("refreshToken")
@Accessors(chain = true)
@AllArgsConstructor
@Setter
@Getter
public class RefreshToken {

    @Id
    private Long id;

    private String value;

}
