package com.alkemy.ong.security;

import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.RolesRepository;
import com.alkemy.ong.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserGeneratorTest {

//    @Autowired
//    RolesRepository roleRepository;
//
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository userRepository){
//        return args -> {
//            UserEntity usuarioAdmin = new UserEntity();
//            usuarioAdmin.setFirstName("Admin");
//            usuarioAdmin.setLastName("Test");
//            usuarioAdmin.setEmail("admin@gmail.com");
//            usuarioAdmin.setPassword(new BCryptPasswordEncoder().encode("123456"));
//            usuarioAdmin.setRole(roleRepository.findById(1L).get());
//            userRepository.save(usuarioAdmin);
//
//            UserEntity usuarioRegular = new UserEntity();
//            usuarioRegular.setFirstName("User");
//            usuarioRegular.setLastName("Test");
//            usuarioRegular.setEmail("user@gmail.com");
//            usuarioRegular.setPassword(new BCryptPasswordEncoder().encode("123456"));
//            usuarioRegular.setRole(roleRepository.findById(2L).get());
//            userRepository.save(usuarioRegular);
//        };
//    }

}
