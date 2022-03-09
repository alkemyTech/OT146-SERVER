package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.ActivityEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.activities.Activity;
import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserGateway;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultUserGateway implements UserGateway {

    private final UserRepository userRepository;

    public DefaultUserGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
                .map(this::toModel)
                .collect(toList());
    }


    @Override
    public List<User> findByDeleted(boolean isDeleted) {
        List<UserEntity> entities = userRepository.findByDeleted(isDeleted);
        return entities.stream()
                .map(this::toModel)
                .collect(toList());
    }

    @Override
    public User findByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        return toModel(entity);
    }

    @Override
    public User findById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        return toModel(entity);
    }


    private User toModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .photo(entity.getPhoto())
                .roleId(entity.getRole().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
