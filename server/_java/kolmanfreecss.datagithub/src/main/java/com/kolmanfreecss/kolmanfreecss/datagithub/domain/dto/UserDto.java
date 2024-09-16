package com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto;

/**
 * @author Kolman-Freecss
 * @version 1.0
 */
public record UserDto(String name, String email) {
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
}
