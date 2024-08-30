package com.example.mini.dto;


import com.example.mini.entity.SpUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpAddressForm {
	
	@Transient
	private Integer id;
	@NotEmpty(message = "도로명을 입력하세요.")
	private String street_name;
	@NotEmpty(message = "건물번호를 입력하세요.")
	private Integer building_number;
	@NotEmpty(message = "상세주소를 입력하세요.")
	private String detail_address;
	@NotEmpty(message = "시,도를 입력하세요.")
	private String city;
	
	public SpAddressForm(String street_name,
			Integer building_number,String detail_address,String city) {
		super();
		this.street_name = street_name;
		this.building_number = building_number;
		this.detail_address = detail_address;
		this.city = city;
	}
	
	

}
