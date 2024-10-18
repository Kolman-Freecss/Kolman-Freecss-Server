package com.kolmanfreecss.kolmanfreecss.datagithub.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Kolman-Freecss
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class GithubData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;
    
    private String email;
    
}
