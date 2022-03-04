package com.alkemy.ong.domain.users;

import org.springframework.stereotype.Service;

import java.util.List;

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


}