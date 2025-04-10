package com.example.maktabproject1.repository;

import com.example.maktabproject1.entity.UserEntity;
import com.example.maktabproject1.entity.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(UserRoleType role);

    List<UserEntity> findByFirstNameIgnoreCase(String firstName);

    List<UserEntity> findByLastNameIgnoreCase(String lastName);

    List<UserEntity> findByEmailIgnoreCase(String email);

}
