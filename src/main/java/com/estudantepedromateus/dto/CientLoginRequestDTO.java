package com.estudantepedromateus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CientLoginRequestDTO {

    private String userName;
    private String password;

    public CientLoginRequestDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken convertToAuth(){
        return new UsernamePasswordAuthenticationToken(this.userName,this.password);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
