package com.adenali.fms.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="asomo_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String name;

}
