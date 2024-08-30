package com.example.mini.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.dto.SpCartForm;
import com.example.mini.dto.SpOrderForm;
import com.example.mini.entity.Product;
import com.example.mini.entity.SpCart;
import com.example.mini.entity.SpOrder;
import com.example.mini.entity.SpUser;
import com.example.mini.service.SpOrderService;
import com.example.mini.service.SpUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class SpOrderController {
	private final SpUserService spUserService;
	private final SpOrderService spOrderService;
	
	@GetMapping("/list")
	public String orderList(Model model, @PathVariable("id") Long id,Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		List<SpOrder> ordersList = user.getOrdersList();
		model.addAttribute("orderList", ordersList);
		return "order_list";
	}
	//장바구니 있는지 탐색후 있으면 추가 없으면 생성
	@GetMapping("/create")
	public String create(SpOrderForm spOrderForm, BindingResult bindingResult,Model model, 
						@PathVariable("id") Long id,Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();
		HashMap<Product, Long> cartList = spcart.getCartList();
		cartList.forEach((key,value) -> {
			System.out.println(key+""+value);
		});
		
		return "order_form";
		}
	
	

}
