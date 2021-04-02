package com.estudantepedromateus.resources;

import com.estudantepedromateus.configurations.TokenService;
import com.estudantepedromateus.dto.CientLoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class ClientLoginResource {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody CientLoginRequestDTO login){
        Authentication auth= authenticationManager.authenticate(login.convertToAuth());
        String token = tokenService.gerarToken(auth);
        return ResponseEntity.ok().headers(h->h.setBearerAuth(token)).body("Login realizado com sucesso!!");

    }

}
