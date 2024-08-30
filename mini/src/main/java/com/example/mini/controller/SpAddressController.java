package com.example.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/list/{id}")
	public String list(Model model, @PathVariable("id") Long id) {
		SpUser user = this.spUserService.findbyId(id);
		List<SpAddress> addressList = user.getAddressList();
		model.addAttribute(addressList);
		return "address_list";
	}
	
	@GetMapping("/create/{id}")
	public String AddressCreate(Model model, @PathVariable("id") Long id) {
		model.addAttribute("method","post");
		return "address_form";
		
	}
	
	
	@PostMapping("/create/{id}")
	public String AddressCreate(@Valid SpAddressForm spAddressForm,BindingResult bindingResult, 
								Model model, @PathVariable("id") Long id) {
		SpUser spuser = this.spUserService.findbyId(id);
		if(bindingResult.hasErrors()) {
			model.addAttribute("spuser",spuser);
			return "address_list";
		}
		this.spAddressService.create(spuser,spAddressForm.getBuilding_number()
				,spAddressForm.getStreet_name(),spAddressForm.getDetail_address()
				,spAddressForm.getCity());
			return "address_list";
	}
	
	@GetMapping("/modify/{id}")
	public String AddressModify(Model model, SpAddressForm spAddressForm, @PathVariable("id") Long id) {
		SpAddress spAddress = this.spAddressService.selectOneAddress(id);
		spAddressForm.setBuilding_number(spAddress.getBuilding_number());
		spAddressForm.setStreet_name(spAddress.getStreet_name());
		spAddressForm.setDetail_address(spAddress.getDetail_address());
		spAddressForm.setCity(spAddress.getCity());
		model.addAttribute("method", "put");
		return "address_form";
	}
	@PutMapping("/modify/{id}")
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
		return "redirect:/address/list";
	}
	@DeleteMapping("/delete/{id}")
	public String AddressDelete(@PathVariable("id") Long id) {
		SpAddress spAddress = this.spAddressService.selectOneAddress(id);
		this.spAddressService.delete(spAddress);
		return "redirect:/address/list";
	}

}
