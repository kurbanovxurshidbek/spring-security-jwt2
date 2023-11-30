package com.springsecurity.jwt2.model.security;

import com.springsecurity.jwt2.model.entity.Role;
import com.springsecurity.jwt2.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

// #principle - 1
public record UserPrinciple(
        String email,
        String password,
        String firstName,
        String lastName,
        Set<Role> roles
) implements UserDetails {


    public static UserPrinciple of(String email, String password, String firstName, String lastName, Set<Role> roles) {
        return new UserPrinciple(
                email,
                password,
                firstName,
                lastName,
                roles

        );
    }

    public static UserPrinciple from(User user) {
        return UserPrinciple.of(
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles()
        );
    }

    public User toEntity() {
        return new User(
                email,
                password,
                firstName,
                lastName,
                roles
        );
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserPrinciple{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}