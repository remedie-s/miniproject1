package com.example.mini.dto;

import java.util.HashMap;

import com.example.mini.entity.Product;

import jakarta.annotation.Nullable;
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
	@Nullable
	public HashMap<Product, Long> cartList;
}
