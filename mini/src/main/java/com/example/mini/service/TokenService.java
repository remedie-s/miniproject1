package com.example.mini.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mini.entity.RefreshToken;
import com.example.mini.entity.SpUser;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.RefreshTokenRepository;

@Service
public class TokenService {

	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private SpUserService spUserService;
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	public String createNewAccessToken(String refreshToken, int hour) {
		if (!tokenProvider.isVaildToken(refreshToken)) {
			throw new IllegalArgumentException("Unexpected token");
		}
		if (!tokenProvider.isValidRefreshToken(refreshToken)) {
			throw new IllegalArgumentException("Unexpected token");
		}
		Long spuserId = findByRefreshToken(refreshToken).getSpuserid();
		SpUser spuser = spUserService.findbyId(spuserId);
		return tokenProvider.geneateToken(spuser, Duration.ofHours(hour));
	}

	public RefreshToken findByRefreshToken(String refreshToken) {
		Optional<RefreshToken> opt = this.refreshTokenRepository.findByRefreshToken(refreshToken);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new DataNotFoundException("user not found");
	}

	public RefreshToken findByUserId(Long Spuserid) {
		Optional<RefreshToken> opt = this.refreshTokenRepository.findById(Spuserid);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new DataNotFoundException("user not found");
	}

	public void saveRefreshToken(Long id, String token) {
		RefreshToken refreshToken = new RefreshToken(id, token);
		this.refreshTokenRepository.save(refreshToken);
	}

}
