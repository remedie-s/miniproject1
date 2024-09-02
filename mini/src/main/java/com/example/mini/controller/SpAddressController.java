package com.example.mini.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	@GetMapping("/list")
	public String addressList(Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		long id = user.getId();
		return "redirect:/address/list/" + id;
	}

	@GetMapping("/list/{id}")
	public String addressList(Model model, @PathVariable("id") Long id, Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		List<SpAddress> addresslist = user.getAddressList();
		model.addAttribute("addresslist", addresslist);
		return "address_list";
	}

	@GetMapping("/detail/{id}")
	public String addressdetail(Model model, @PathVariable("id") Long id, SpAddressForm spAddressForm) {
		SpAddress address = this.spAddressService.getoneAddress(id);
		// model.addAttribute("address", address);
		return "address_detail"+id;
	}
	

	@GetMapping("/create")
	public String AddressCreate(SpAddressForm spAddressForm, Principal principal, Model model) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		Long id = user.getId();
		model.addAttribute("spAddressForm", new SpAddressForm());
		return "addressform";
	}

	@PostMapping("/create")
	public String AddressCreate(@Valid SpAddressForm spAddressForm, BindingResult bindingResult,
			Model model,  Principal principal) {
		String name = principal.getName();
		System.out.println("1");
		SpUser user = spUserService.findbyUsername(name);
		Long id = user.getId();
		//같은지 검증할까?
		SpAddress address;
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "address_list";
		}
		System.out.println("2");
		address = this.spAddressService.create(user, spAddressForm.getStreet_name(), spAddressForm.getBuilding_number(), 
				spAddressForm.getDetail_address(), spAddressForm.getCity());
				System.out.println("3");
		return "address_list";
	}

	@GetMapping("/modify/{id}")
	public String AddressModify(Model model, SpAddressForm spAddressForm, @PathVariable("id") Long id,
			Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		id = user.getId();
		SpAddress spAddress = this.spAddressService.selectOneAddress(id);
		spAddressForm.setBuilding_number(spAddress.getBuilding_number());
		spAddressForm.setStreet_name(spAddress.getStreet_name());
		spAddressForm.setDetail_address(spAddress.getDetail_address());
		spAddressForm.setCity(spAddress.getCity());
		model.addAttribute("method", "put");
		return "address_form";
	}

	@PutMapping("/modify/{id}")
	public String AddressModify(@Valid SpAddressForm spAddressForm, BindingResult bindingResult,
			@PathVariable("id") Long id) {
		if (bindingResult.hasErrors()) {
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

	@GetMapping("/delete/{id}")
	public String AddressDelete(@PathVariable("id") Long id) {
		SpAddress spAddress = this.spAddressService.selectOneAddress(id);
		this.spAddressService.delete(spAddress);
		return "redirect:/address/list";
	}

}
