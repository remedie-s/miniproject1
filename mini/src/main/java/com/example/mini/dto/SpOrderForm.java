package com.example.mini.dto;

import com.example.mini.config.OrderStatus;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpOrderForm {
	@Transient
	private Long id;
	private OrderStatus status = OrderStatus.READY;
	private boolean request = false;

}
