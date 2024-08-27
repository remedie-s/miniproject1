package com.example.mini.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.mini.config.JwtProperties;
import com.example.mini.entity.SpUser;
import com.example.mini.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenProvider {
	
	private final JwtProperties jwtProperties;
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	public String geneateToken(SpUser user, Duration expriedAt) {
		Date now = new Date();
		return createToken(new Date(now.getTime()+expriedAt.toMillis()), user);
	}

	private String createToken(Date expriry, SpUser user) {
		Date now = new Date();
		
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setIssuer(jwtProperties.getIssuer())
				.setIssuedAt(now)
				.setExpiration(expriry)
				.setSubject(user.getE_mail())
				.claim("id", user.getId())
				.signWith(SignatureAlgorithm.HS256,jwtProperties.getSecretKey())
				.compact();
								
	}

	public boolean isVaildToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(jwtProperties.getSecretKey())
				.parseClaimsJws(token);
				return true;
		} catch (SignatureException e) {
			e.printStackTrace();
			return false;
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedJwtException e) {
			e.printStackTrace();
			return false;
		} catch (MalformedJwtException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Long getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("id",Long.class);
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(jwtProperties.getSecretKey())
				.parseClaimsJws(token)
				.getBody()
				;
	}

}
