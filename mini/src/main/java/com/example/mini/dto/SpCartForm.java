package com.example.mini.dto;

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
	private Long productid;
	private Long quantity;
	
}
