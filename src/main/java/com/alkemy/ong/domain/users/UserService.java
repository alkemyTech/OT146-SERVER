package com.alkemy.ong.domain.users;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

   // public Boolean existsByEmail(String email){
  //      return userGateway.existsByEmail(email);
  //  }

    public User save(User user){
        return userGateway.create(user);
    }
}
