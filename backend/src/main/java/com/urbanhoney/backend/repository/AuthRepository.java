package com.urbanhoney.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.UserEntity;
import com.urbanhoney.backend.usecase.dto.UserDto;


@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findById(Integer id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :emailOrUsername OR u.username = :emailOrUsername")
    Optional<UserEntity> findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);

    List<UserEntity> findAll();

    void save(UserDto userDto);
}
