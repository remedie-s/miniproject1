package com.example.mini.dto;

import com.example.mini.entity.Product;
import com.example.mini.entity.SpCart;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;

public class CartDetailForm {
	
	@Transient
	private Integer id;
	@NotEmpty(message = "도로명을 입력하세요.")
	private Integer quantity;
	
	

}
