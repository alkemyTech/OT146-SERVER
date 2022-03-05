package com.alkemy.ong.domain.users;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserGateway {

    private final UserGateway userGateway;

    public UserService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public List<User> findAll() {
        return userGateway.findAll();
    }

    @Override
    public List<User> findByDeleted(boolean isDeleted) {
        return userGateway.findByDeleted(isDeleted);
    }

    @Override
    public User findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userGateway.findById(id);
    }


}
