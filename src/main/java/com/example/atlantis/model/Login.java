package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "login")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class Login {


    @Id
    @JoinColumn(name = "email")
    private String email;

    @JoinColumn(name = "password")
    private String password;

    @JoinColumn(name = "rol")
    private Rol rol;

}
