package com.example.mini.dto;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.example.mini.config.OrderStatus;
import com.example.mini.entity.SpCart;
import com.example.mini.entity.SpUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;

public class SpOrderForm {
	
	private Integer id;
	@NotEmpty
	public HashMap<Long, Long> orderList = new HashMap<Long, Long>();
	@NotEmpty
	private OrderStatus status;
	@NotEmpty
	private String request;

}
