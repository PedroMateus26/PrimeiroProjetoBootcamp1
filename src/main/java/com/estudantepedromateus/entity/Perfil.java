package com.estudantepedromateus.entity;

import org.springframework.security.core.GrantedAuthority;

public class Perfil implements GrantedAuthority {

    private String id;
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
