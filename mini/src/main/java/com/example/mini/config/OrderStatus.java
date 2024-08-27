package com.example.mini.config;

import lombok.Getter;

@Getter
public enum OrderStatus {
	READY("ORDER_READY"), START("ORDER_START"), ARRIVE("ORDER_ARRIVE"), END("ORDER_END");

	
	private OrderStatus(String value) {
		this.value = value;
	}

	private String value;
	
	

}
