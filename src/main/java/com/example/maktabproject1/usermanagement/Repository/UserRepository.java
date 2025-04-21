package com.example.maktabproject1.usermanagement.Repository;

import com.example.maktabproject1.usermanagement.entity.UserEntity;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(UserRoleType role);

    List<UserEntity> findByFirstNameIgnoreCase(String firstName);

    List<UserEntity> findByLastNameIgnoreCase(String lastName);

    UserEntity findByEmailIgnoreCase(String email);

    List<UserEntity> findByStatus(UserStatusType status);

    Optional<UserEntity> findById(Long id);

}
