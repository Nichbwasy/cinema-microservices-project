package com.cinema.common.utils.authorizations.jwt;

import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class JwtClientUtils {
    public static JwtClientAuthentication generate(Claims claims) {
        JwtClientAuthentication jwtInfoToken = new JwtClientAuthentication();
        jwtInfoToken.setAuthorities(getRoles(claims));
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
