package com.alkemy.ong.domain.security;

import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User model = service.findByEmail(email);
        if (model == null) {
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(getAuthority(model));
            return new org.springframework.security.core.userdetails.User(model.getEmail(), model.getPassword(), authorities);
        }
    }

    private GrantedAuthority getAuthority(User model) {

        if (model.getRoleId() == 1L) {
            return new SimpleGrantedAuthority("ROLE_ADMIN");
        }
        if (model.getRoleId() == 2L) {
            return new SimpleGrantedAuthority("ROLE_USER");
        }
        return null;
    }
}
