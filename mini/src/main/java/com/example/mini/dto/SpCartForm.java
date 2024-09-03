package com.example.mini.dto;

import com.example.mini.entity.Product;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpCartForm {

	@Transient
	private Long id;
	private Product product;
	private Long quantity;
	
}
