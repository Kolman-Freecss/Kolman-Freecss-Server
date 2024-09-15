package com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto;

public record UserDto(String name, String email) {
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
}
