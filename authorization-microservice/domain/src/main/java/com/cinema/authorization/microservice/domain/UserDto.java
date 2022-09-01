package com.cinema.authorization.microservice.domain;

import com.cinema.common.utils.authorizations.providers.ProviderTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private ProviderTypes provider;
    private Collection<RoleDto> roles;
    private Boolean enabled;

}
