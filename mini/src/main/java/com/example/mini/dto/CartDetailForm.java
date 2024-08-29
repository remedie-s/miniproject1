package com.example.mini.dto;

import com.example.mini.entity.Product;
import com.example.mini.entity.SpCart;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CartDetailForm {
	
	@Transient
	private Integer id;
	@NotEmpty(message = "수량은 비어선 안됩니다.")
	private Integer quantity;
	
	

}
