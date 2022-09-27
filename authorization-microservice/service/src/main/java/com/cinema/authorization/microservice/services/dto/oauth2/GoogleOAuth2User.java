package com.cinema.authorization.microservice.services.dto.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class GoogleOAuth2User implements OAuth2User {
    private OAuth2AuthenticatedPrincipal oauth2User;

    public GoogleOAuth2User(OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        this.oauth2User = oAuth2AuthenticatedPrincipal;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.getAttribute("email");
    }

}
