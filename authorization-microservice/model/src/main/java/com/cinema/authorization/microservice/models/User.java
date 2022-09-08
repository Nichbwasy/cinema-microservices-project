package com.cinema.authorization.microservice.models;

import com.cinema.common.utils.authorizations.providers.ProviderTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username is mandatory!")
    @Size(min = 3, max = 128, message = "Username size must be in between 3 and 128 characters!")
    @Column(name = "username", unique = true, nullable = false, length = 128)
    private String username;

    @NotNull(message = "Email is mandatory!")
    @Size(min = 5, max = 128, message = "Email size must be in between 5 and 128 characters!")
    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @NotNull(message = "Password is mandatory!")
    @Size(min = 4, max = 64, message = "Password size must be in between 4 and 64 characters!")
    @Column(name = "password", unique = true, nullable = false, length = 64)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", length = 32)
    private ProviderTypes provider;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name =  "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Collection<Role> roles;

    @Column(name = "enabled", nullable = false, columnDefinition="boolean default false")
    private Boolean enabled;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
