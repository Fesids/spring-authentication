package com.application.app.models.entities.auth;


import com.application.app.models.entities.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@Entity
@Getter
@Setter
@Table(name = "permissions")
public class Permission extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;


    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }






}
