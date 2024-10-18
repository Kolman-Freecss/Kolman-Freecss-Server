package com.kolmanfreecss.kolmanfreecss.datagithub.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author Kolman-Freecss
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "app_user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;
    
    private String email;
    
    @Column(name = "last_login")
    private Date lastLogin;
}
