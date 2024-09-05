package com.example.mini.dto;

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
	private String status;
	private String request;

}
