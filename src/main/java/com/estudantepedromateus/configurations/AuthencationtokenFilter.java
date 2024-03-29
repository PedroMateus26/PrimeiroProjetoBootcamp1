package com.estudantepedromateus.configurations;

import com.estudantepedromateus.entity.Client;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthencationtokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    public AuthencationtokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=recuperarToken(request);
        boolean isValid=tokenService.isValid(token);
        if(isValid){
            Client client = tokenService.getClient(token);
            UsernamePasswordAuthenticationToken authPass=new UsernamePasswordAuthenticationToken(client,null, client.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authPass);
        }
        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token==null||token.isEmpty()||!token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7);
    }


}
