package com.example.mini.dto;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
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
	@Min(1)
	private Integer building_number;
	@NotEmpty(message = "상세주소를 입력하세요.")
	private String detail_address;
	@NotEmpty(message = "시,도를 입력하세요.")
	private String city;

	public SpAddressForm(String street_name,
			Integer building_number, String detail_address, String city) {
		super();
		this.street_name = street_name;
		this.building_number = building_number;
		this.detail_address = detail_address;
		this.city = city;
	}

}
