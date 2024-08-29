package com.example.mini.dto;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.example.mini.entity.Product;
import com.example.mini.entity.SpOrder;
import com.example.mini.entity.SpUser;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class SpCartForm {
	
	@Transient
	private Integer id;
	public HashMap<Long, Long> cartList = new HashMap<Long, Long>();
}
