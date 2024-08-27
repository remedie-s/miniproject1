package com.example.mini.config;

import lombok.Getter;

@Getter
public enum SpUserGrade {
	ADMIN("ROLE_ADMIN"),
	GOLD("ROLE_USER"),
	SILVER("ROLE_USER"),
	BRONZE("ROLE_USER"),
	SELLER("ROLE_SELLER");
	//TODO 롤 구분해야하나 고민
	SpUserGrade(String value) {
		this.value=value;
	}
	private String value;
	
	

}

