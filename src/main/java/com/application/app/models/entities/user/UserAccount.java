package com.application.app.models.entities.user;

import com.application.app.models.entities.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Entity
@Table(name = "users_accounts")
public class UserAccount extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String token;

    private long expireAt;

    public boolean isExpired() { return expireAt < new Date().getTime();}

}
