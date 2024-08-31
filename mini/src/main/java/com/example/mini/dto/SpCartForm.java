package com.example.mini.dto;

import java.util.HashMap;

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
	private Integer id;
	@Nullable
	public HashMap<Long, Long> cartList = new HashMap<Long, Long>();
}
