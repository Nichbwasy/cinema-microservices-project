package com.cinema.common.utils.authorizations.jwt;

import com.cinema.common.utils.authorizations.jwt.exceptions.ClientJwtException;
import com.cinema.common.utils.authorizations.jwt.exceptions.ClientJwtTokenNotFoundException;
import com.cinema.common.utils.authorizations.jwt.exceptions.ClientJwtTokenValidationException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends GenericFilterBean {

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String BEARER = "Bearer ";

    private final JwtClientTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException, ClientJwtException {
        String path = ((HttpServletRequest) servletRequest).getServletPath();
        if (path.equals("/login") || path.equals("/register")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String token = getTokenFromRequest((HttpServletRequest) servletRequest);
            if (jwtTokenProvider.validateAccessToken(token)) {
                log.info("Token is valid.");
                UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                log.error("Token validation exception!");
                throw new ClientJwtTokenNotFoundException("Token validation exception!");
            }
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        Claims claims = jwtTokenProvider.getAccessClaims(token);
        JwtClientAuthentication jwtInfoToken = JwtClientUtils.generate(claims);
        return new UsernamePasswordAuthenticationToken(jwtInfoToken.getUsername(), null, jwtInfoToken.getAuthorities());
    }

    private String getTokenFromRequest(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(AUTHORIZATION_HEADER);
        if (token != null) {
            if (token.startsWith(BEARER)) {
                return token.substring(7);
            } else {
                log.error("Token invalid!");
                throw new ClientJwtTokenNotFoundException("Token not found!");
            }
        } else {
            log.error("Token not found!");
            throw new ClientJwtTokenValidationException("Token not found!");
        }
    }
}
