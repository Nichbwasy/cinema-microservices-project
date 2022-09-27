package com.cinema.authorization.microservice.controllers.security;

import com.cinema.authorization.microservice.controllers.dto.JwtTokensResponse;
import com.cinema.authorization.microservice.controllers.security.jwt.JwtAuthenticationRequestFilter;
import com.cinema.authorization.microservice.controllers.security.services.JwtAuthenticationService;
import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.services.UsersService;
import com.cinema.authorization.microservice.services.dto.oauth2.GoogleOAuth2User;
import com.cinema.authorization.microservice.services.implimets.GoogleOAuth2UserService;
import com.cinema.common.utils.authorizations.roles.UserRoles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.security.auth.message.AuthException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthenticationRequestFilter jwtRequestFilter;
    private final GoogleOAuth2UserService googleOAuth2UserService;

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private UsersService usersService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/login", "/register", "/error").permitAll()
                .antMatchers("/api/test/admin").hasAnyAuthority(UserRoles.ADMIN)
                .antMatchers("/api/test/user").hasAnyAuthority(UserRoles.USER, UserRoles.ADMIN)
                .antMatchers("/login/oauth2/**", "/api/test/all").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                    .loginPage("/login/oauth2")
                        .authorizationEndpoint()
                            .baseUri("/login/oauth2/authorization")
                            .and()
                    .userInfoEndpoint()
                        .userService(googleOAuth2UserService)
                        .and()
                    .successHandler((request, response, authentication) -> {
                        try {
                            GoogleOAuth2User googleOAuth2User = new GoogleOAuth2User((OAuth2AuthenticatedPrincipal) authentication.getPrincipal());
                            UserDto googleUser = usersService.processGoogleOAuthPostLogin(googleOAuth2User);
                            JwtTokensResponse tokens = jwtAuthenticationService.logInAuthenticatedUser(googleUser);

                            response.setHeader("access_token", tokens.getAccessToken());
                            response.setHeader("refresh_token", tokens.getRefreshToken());
                        } catch (Exception e) {
                            log.error("Error was occurred while authentication via google! {}", e.getMessage());
                        }
                    })
                .and()
                .build();

    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}
