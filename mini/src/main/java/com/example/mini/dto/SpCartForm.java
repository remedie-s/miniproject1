package com.example.mini.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpCartForm {

	
	private Long id;
	@Min(1)
	private Long productid;
	@Min(1)
	private Long quantity;
	@Min(1)
	private Long subtotal;
	
}
