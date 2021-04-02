package com.estudantepedromateus.configurations;

import com.estudantepedromateus.entity.Client;
import com.estudantepedromateus.repositories.ClientRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${a.jwt.expiration}")
    private String expiration;
   // @Value("${a.jwt.secret}")
    private String secret="abcd";

    @Autowired
    private ClientRepository clientRepository;

    public String gerarToken(Authentication authentication){
        Client client = (Client) authentication.getPrincipal();
        Date hoje=new Date();
        Date dateExpiration=new Date(hoje.getTime()+Long.parseLong(expiration));
        return Jwts.builder().setIssuer("a").setSubject(client.getId().toString())
                .setIssuedAt(hoje).setExpiration(dateExpiration).signWith(SignatureAlgorithm.HS256,secret).compact();

    }

    public boolean isValid(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Client getClient(String token){
        Long idClient= Long.parseLong(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
        return clientRepository.findById(idClient).get();

    }
}
