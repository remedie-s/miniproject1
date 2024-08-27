package com.example.mini.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.mini.config.JwtProperties;
import com.example.mini.entity.RefreshToken;
import com.example.mini.entity.SpUser;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.RefreshTokenRepository;

public class TokenService {
	
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private SpUserService spUserService;
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	public String createNewAccessToken(String refreshToken, int hour) {
		if(!tokenProvider.isVaildToken(refreshToken)) {
			throw new IllegalArgumentException("Unexpected token");
		}
		if(!tokenProvider.isValidRefreshToken(refreshToken)) {
			throw new IllegalArgumentException("Unexpected token");
		}
		Long userId = findByRefreshToken(refreshToken).getUserId();
		SpUser spuser= spUserService.findbyId(userId);
		return tokenProvider.geneateToken(spuser, Duration.ofHours(hour));
	}

	public RefreshToken findByRefreshToken(String refreshToken) {
		Optional<RefreshToken> opt = this.refreshTokenRepository.findbyRefreshToken(refreshToken);
		if(opt.isPresent()) {
			return opt.get();			
		}
		throw new DataNotFoundException("user not found");
	}
	
	public RefreshToken findByUserId(Long Spuserid) {
		Optional<RefreshToken> opt = this.refreshTokenRepository.findbySpUserId(Spuserid);
		if(opt.isPresent()) {
			return opt.get();			
		}
		throw new DataNotFoundException("user not found");
	}
	
	public void saveRefreshToken(Long id, String token) {
		RefreshToken refreshToken = new RefreshToken(id, token);
		this.refreshTokenRepository.save(refreshToken);
	}
	
}
