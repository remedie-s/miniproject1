package com.example.mini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

}
