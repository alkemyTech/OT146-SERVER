package com.alkemy.ong.domain.users;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserGateway userGateway;

    public UserService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }


    public List<User> findAll() {
        return userGateway.findAll();
    }


    public List<User> findByDeleted(boolean isDeleted) {
        return userGateway.findByDeleted(isDeleted);
    }


    public User findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    public User findById(Long id) {
        return userGateway.findById(id);
    }

    public User save(User user){
        return userGateway.create(user);
    }

    public boolean existsByEmail(String email){
        return userGateway.existsByEmail(email);
    }

}
