package com.cinema.authorization.microservice.controllers.security.jwt;

import com.cinema.authorization.microservice.domain.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey ACCESS_SECRET;
    private final SecretKey REFRESH_SECRET;
    private final static Integer ACCESS_TOKEN_LIFETIME = 30 * 60; //30min access token life time
    private final static Integer REFRESH_TOKEN_LIFETIME = 30 * 24 * 60 * 60; //30d refresh token life time

    public JwtTokenProvider(@Value("${jwt.secret.key.access}") String jwtAccessSecret,
                            @Value("${jwt.secret.key.refresh}") String jwtRefreshSecret) {
        this.ACCESS_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.REFRESH_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    public String generateAccessJwtToken(@NonNull UserDto user){
        LocalDateTime now = LocalDateTime.now();
        Instant timeInstant = now.plusSeconds(ACCESS_TOKEN_LIFETIME).atZone(ZoneId.systemDefault()).toInstant();
        Date expiration = Date.from(timeInstant);
        String accessToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .signWith(ACCESS_SECRET)
                .claim("roles", user.getRoles())
                .claim("email", user.getEmail())
                .compact();
        log.info("New authentication token for user '{}' has been generated.", user.getUsername());
        return accessToken;
    }

    public String generateRefreshJwtToken(@NonNull UserDto user){
        LocalDateTime now = LocalDateTime.now();
        Instant timeInstant = now.plusSeconds(REFRESH_TOKEN_LIFETIME).atZone(ZoneId.systemDefault()).toInstant();
        Date expiration = Date.from(timeInstant);
        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .signWith(REFRESH_SECRET)
                .compact();
        log.info("New refresh token for user '{}' has been generated.", user.getUsername());
        return refreshToken;
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, ACCESS_SECRET);
    }

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token, REFRESH_SECRET);
    }

    private boolean validateToken(@NonNull String token, @NonNull SecretKey secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired!", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported JWT!", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed JWT!", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature!", sEx);
        } catch (Exception e) {
            log.error("invalid token!", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, ACCESS_SECRET);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, REFRESH_SECRET);
    }

    private Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
