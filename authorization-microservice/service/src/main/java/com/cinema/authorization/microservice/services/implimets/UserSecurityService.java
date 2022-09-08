package com.cinema.authorization.microservice.services.implimets;

import com.cinema.authorization.microservice.exceptions.services.users.UserNotFoundException;
import com.cinema.authorization.microservice.models.User;
import com.cinema.authorization.microservice.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (usersRepository.existsByUsername(username)) {
            User user = usersRepository.getByUsername(username);
            Set<SimpleGrantedAuthority> authorities = getAuthority(user);
            log.info("Details for user '{}' was found.", username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } else {
            log.warn("Can't find user '{}' to load details!", username);
            throw new UserNotFoundException(String.format("Can't find user '%s' to load details!", username));
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        log.info("Authorities '{}' for user '{}' have been found.", authorities.toString(), user.getUsername());
        return authorities;
    }

}
