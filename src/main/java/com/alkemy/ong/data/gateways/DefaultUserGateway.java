package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.RolesRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserGateway;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultUserGateway implements UserGateway {

    private final UserRepository userRepository;
    private final RolesRepository roleRepository;

    public DefaultUserGateway(UserRepository userRepository, RolesRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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


    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User create(User user) {
        UserEntity entity = toEntity(user);
        entity.setCreatedAt(LocalDateTime.now());
        return toModel(userRepository.save(entity));
    }

    @Override
    public User update(Long id, User user) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        if (!Objects.equals(entity.getEmail(), user.getEmail())
                && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("Email taken!");
        }
        user.setId(id);
        entity = toEntity(user);
        entity.setUpdatedAt(LocalDateTime.now());
        return toModel(userRepository.save(entity));
    }

    private UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .photo(user.getPhoto())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .role(roleRepository.findById(user.getRoleId()).orElseThrow(
                        () -> new ResourceNotFoundException("Rol not found")
                ))
                .build();
    }

    private User toModel(UserEntity entity) {
        String roleName = entity.getRole().getId() == 1L ? "ADMIN" : "USER";
        return User.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .photo(entity.getPhoto())
                .roleId(entity.getRole().getId())
                .roleName(roleName)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
