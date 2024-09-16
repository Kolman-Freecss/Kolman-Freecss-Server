package com.kolmanfreecss.kolmanfreecss.datagithub.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
    private UUID id;
    
    private String name;
    
    private String email;
    
}
