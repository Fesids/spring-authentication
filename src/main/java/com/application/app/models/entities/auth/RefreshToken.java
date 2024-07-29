package com.application.app.models.entities.auth;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;

//@RedisHash("refreshToken")
@Accessors(chain = true)
@AllArgsConstructor
@Setter
@Getter

@Data
@Entity
@Table(name = "refreshTokens")
public class RefreshToken {

    /*@Id
    private Long id;*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

}
