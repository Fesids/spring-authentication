package com.application.app.models.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Accessors(chain = true)
@Getter
@Setter
public abstract class BaseModel implements Serializable {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

}
