package com.example.mini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.SpUser;

public interface SpUserRepository extends JpaRepository<SpUser, Long> {

	Optional<SpUser> findByUsername(String username);

}
