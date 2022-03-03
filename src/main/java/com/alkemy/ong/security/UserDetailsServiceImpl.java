package com.alkemy.ong.security;

import com.alkemy.ong.data.entity.RolesEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.RolesRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.web.exceptions.BadRequestException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static UserRepository userRepository;

    private static RolesRepository roleRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, RolesRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new BadRequestException("User not found")
                );
        RolesEntity roleUser = roleRepository.findById(userEntity.getRole().getId())
                .orElseThrow(
                        () -> new BadRequestException("Role not found")
                );
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(getAuthority(roleUser));
        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }

    private GrantedAuthority getAuthority(RolesEntity roleEntity) {

        if (roleEntity.getName().equalsIgnoreCase("Administrador")) {
            return new SimpleGrantedAuthority("ROLE_ADMIN");
        }
        if (roleEntity.getName().equalsIgnoreCase("Regular")) {
            return new SimpleGrantedAuthority("ROLE_USER");
        }
        return null;
    }
}
