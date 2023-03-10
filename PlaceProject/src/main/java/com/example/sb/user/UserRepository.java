package com.example.sb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	// select * from user where userid = ?
	Optional<UserEntity> findByUserId(String userId);
	Optional<UserEntity> findByEmail(String email);
}
