package com.example.mini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.SpUser;

public interface SpUserRepository extends JpaRepository<SpUser, Long> {

}
