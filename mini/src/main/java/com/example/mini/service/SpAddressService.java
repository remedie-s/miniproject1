package com.example.mini.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mini.entity.SpAddress;
import com.example.mini.entity.SpUser;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.SpAddressRepository;





@Service
public class SpAddressService {
	@Autowired
	private SpAddressRepository spAddressRepository;

	public void create(SpUser spuser, Integer building_number, String street_name, String detail_address, String city) {
		SpAddress spAddress = new SpAddress();
		spAddress.setBuilding_number(building_number);
		spAddress.setStreet_name(street_name);
		spAddress.setDetail_address(detail_address);
		spAddress.setCity(city);
		spAddress.setSpuser(spuser);
		this.spAddressRepository.save(spAddress);
	} 
	
	public void modify(SpAddress spAddress) {
		this.spAddressRepository.save(spAddress);
	}
	
	public void delete(SpAddress spAddress) {
		this.spAddressRepository.delete(spAddress);
	}

	public SpAddress selectOneAddress(Long id) {
		Optional<SpAddress> opad = this.spAddressRepository.findById(id);
		if (opad.isPresent()) {
			return opad.get();
			
		}
		throw new DataNotFoundException("해당 주소 번호가 없습니다");
	}
	
	

}
