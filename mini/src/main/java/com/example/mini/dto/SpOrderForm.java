package com.example.mini.dto;

import com.example.mini.config.OrderStatus;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpOrderForm {
	@Transient
	private Long id;
	@NotEmpty(message = "주문상황은 비어선 안됩니다.")
	private OrderStatus status;
	@NotEmpty(message = "요구사항은 비어선 안됩니다.")
	private String request;

}
