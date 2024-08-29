package com.example.mini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.mini.dto.SpAddressForm;
import com.example.mini.entity.SpAddress;
import com.example.mini.entity.SpUser;
import com.example.mini.service.SpAddressService;
import com.example.mini.service.SpUserService;

import jakarta.validation.Valid;


@RequestMapping("/address")
@Controller
public class SpAddressController {
	@Autowired
	private SpAddressService spAddressService;
	@Autowired
	private SpUserService spUserService;
	
	
	
	@PostMapping("/create/{id}")
	public String AddressCreate(@Valid SpAddressForm spAddressForm,BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
		SpUser spuser = this.spUserService.findbyId(id);
		if(bindingResult.hasErrors()) {
			model.addAttribute("spuser",spuser);
			return "address_form";
		}
		this.spAddressService.create(spuser,spAddressForm.getBuilding_number()
				,spAddressForm.getStreet_name(),spAddressForm.getDetail_address()
				,spAddressForm.getCity());
		return "redirect:/spuser/"+spAddressForm.getId();
	}
	@GetMapping("/modify/{id}")
	public String AddressModify(SpAddressForm spAddressForm, @PathVariable("id") Long id) {
		SpAddress spAddress = this.spAddressService.selectOneAddress(id);
		spAddressForm.setBuilding_number(spAddress.getBuilding_number());
		spAddressForm.setStreet_name(spAddress.getStreet_name());
		spAddressForm.setDetail_address(spAddress.getDetail_address());
		spAddressForm.setCity(spAddress.getCity());
		return "address_form";
	}
	@PostMapping("/modify/{id}")
	public String AddressModify(@Valid SpAddressForm spAddressForm, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(bindingResult.hasErrors()) {
		return "address_form";
		}
		SpAddress spAddress = this.spAddressService.selectOneAddress(id);
		spAddress.setBuilding_number(spAddressForm.getBuilding_number());
		spAddress.setStreet_name(spAddressForm.getStreet_name());
		spAddress.setDetail_address(spAddressForm.getDetail_address());
		spAddress.setCity(spAddressForm.getCity());
		this.spAddressService.modify(spAddress);
		return "redirect:/spuser/"+spAddress.getSpuser().getId();
	}
	@GetMapping("/delete/{id}")
	public String AddressDelete(@PathVariable("id") Long id) {
		SpAddress spAddress = this.spAddressService.selectOneAddress(id);
		this.spAddressService.delete(spAddress);
		return "redirect:/spuser/";
	}

}
