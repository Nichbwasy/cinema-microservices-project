package com.cinema.authorization.microservice.controllers.security.jwt;

import com.cinema.authorization.microservice.domain.RoleDto;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setAuthorities(getRoles(claims));
        jwtInfoToken.setEmail(claims.get("email", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        jwtInfoToken.setAuthenticated(true);
        return jwtInfoToken;
    }

    private static Collection<SimpleGrantedAuthority> getRoles(Claims claims) {
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<String> roles = claims.get("roles", List.class);
        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));
        return authorities;
    }
}
