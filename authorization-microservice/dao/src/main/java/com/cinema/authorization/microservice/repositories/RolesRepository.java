package com.cinema.authorization.microservice.repositories;

import com.cinema.authorization.microservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Boolean existsByName(String name);
    Role getByName(String name);
}
