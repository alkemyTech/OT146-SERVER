package com.alkemy.ong.domain.users;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    List<User> findAll();

    List<User> findByDeleted(boolean isDeleted);

    User findByEmail(String email);

}
