package com.example.mini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.RefreshToken;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	Optional<RefreshToken> findbyRefreshToken(String refreshToken);
	Optional<RefreshToken> findbySpUserId(Long spuserId);

	

}
