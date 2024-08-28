package com.example.mini.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="spuserid", updatable =false)
	private long spuserid;
	
	@Column(nullable = false)
	private String refreshToken;
	
	private LocalDateTime createdate;
	
	public RefreshToken(long spuserid, String refreshToken) {
		super();
		this.spuserid = spuserid;
		this.refreshToken = refreshToken;
		this.createdate = LocalDateTime.now();
	}
	
	public RefreshToken update(String newRefreshToken) {
		this.refreshToken=newRefreshToken;
		return this;
	}

}
