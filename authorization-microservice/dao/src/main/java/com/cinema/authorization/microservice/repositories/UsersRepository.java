package com.cinema.authorization.microservice.repositories;

import com.cinema.authorization.microservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
    Boolean existsByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User getByUsername(String username);
    User getByEmail(String email);
}
