package com.example.mini.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SpAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private SpUser spuser;
	private String street_name;
	private Integer building_number;
	private String detail_address;
	private String city;

	public SpAddress(SpUser spuser, String street_name, Integer building_number, String detail_address, String city) {
		this.spuser = spuser;
		this.street_name = street_name;
		this.building_number = building_number;
		this.detail_address = detail_address;
		this.city = city;
	}

}
