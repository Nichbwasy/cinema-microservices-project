package com.cinema.common.utils.authorizations.jwt;

import com.cinema.common.utils.authorizations.jwt.dto.ApiClientDto;
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
public class JwtClientTokenProvider {

    private final SecretKey CLIENT_ACCESS_SECRET;
    private final static Integer CLIENT_ACCESS_TOKEN_LIFETIME = 30; //30 sec access token life time

    public JwtClientTokenProvider(@Value("${jwt.client.secret.key.access}") String jwtAccessSecret) {
        this.CLIENT_ACCESS_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }

    public String generateClientAccessJwtToken(@NonNull ApiClientDto client){
        LocalDateTime now = LocalDateTime.now();
        Instant timeInstant = now.plusSeconds(CLIENT_ACCESS_TOKEN_LIFETIME).atZone(ZoneId.systemDefault()).toInstant();
        Date expiration = Date.from(timeInstant);
        String accessToken = Jwts.builder()
                .setSubject(client.getApiClintName())
                .setExpiration(expiration)
                .signWith(CLIENT_ACCESS_SECRET)
                .claim("roles", client.getApiClientRoles())
                .compact();
        log.info("New authentication token for client '{}' has been generated.", client.getApiClintName());
        return accessToken;
    }
    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, CLIENT_ACCESS_SECRET);
    }

    private boolean validateToken(@NonNull String token, @NonNull SecretKey secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired! {}!", expEx.getMessage());
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported JWT! {}!", unsEx.getMessage());
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed JWT! {}!", mjEx.getMessage());
        } catch (SignatureException sEx) {
            log.error("Invalid signature! {}!", sEx.getMessage());
        } catch (Exception e) {
            log.error("invalid token! {}!", e.getMessage());
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, CLIENT_ACCESS_SECRET);
    }

    private Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
