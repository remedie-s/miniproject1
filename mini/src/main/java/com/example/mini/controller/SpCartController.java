package com.example.mini.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.dto.SpAddressForm;
import com.example.mini.dto.SpCartForm;
import com.example.mini.entity.Product;
import com.example.mini.entity.SpCart;
import com.example.mini.entity.SpUser;
import com.example.mini.service.SpCartService;
import com.example.mini.service.SpUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/cart")
@Controller
@RequiredArgsConstructor
public class SpCartController {
	private final SpCartService spCartService;
	private final SpUserService spUserService;
	
	
	
	@GetMapping("/add/{id}")
	public void addCart(@Valid SpCartForm spCartForm,BindingResult bindingResult,
			@ModelAttribute("product") Product product,
			@ModelAttribute("quantity") Long quantity,
			Model model, @PathVariable("id") Long id){
		SpUser user = this.spUserService.findbyId(id);
		SpCart spcart = user.getSpcart();
		Long productid = product.getId();
		// 앞에는 브라우저에서 제품ID받아서 제품넣고, 뒤에는 수량 받아서 수량 넣어야함
		// 현재 로그인 하고있는 사람 ID 받아와야함
		// TODO 고쳐야함
		spcart.cartList.put(productid, quantity);
	}
	@DeleteMapping("/delete/{id}")
	public void deleteCart(@PathVariable("id") Long id) {
		SpUser user = this.spUserService.findbyId(id);
		SpCart spcart = user.getSpcart();
		Long cartid = spcart.getId();
		this.spCartService.delete(cartid);
	}
	

}
