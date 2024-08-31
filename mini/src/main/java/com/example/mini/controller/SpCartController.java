package com.example.mini.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/list/{id}")
	public String list(@Valid SpCartForm spCartForm, BindingResult bindingResult, Model model,
			@PathVariable("id") Long id, Principal principal) {
		// PathVariable 있어야할까?
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();
		model.addAttribute(spcart);
		// 키값하고 밸류값 따로 ArrayList로 보내는게 나을까?
		return "cart_list";
	}

	@GetMapping("/add/{id}")
	public void addCart(@Valid SpCartForm spCartForm, BindingResult bindingResult,
			@ModelAttribute("product") Product product,
			@ModelAttribute("quantity") Long quantity,
			Model model, @PathVariable("id") Long id,
			Principal principal) {
		// 현재 로그인 하고있는 사람 ID 받아와야함
		// PathVariable 있어야할까?
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();

		// 앞에는 브라우저에서 제품ID받아서 제품넣고, 뒤에는 수량 받아서 수량 넣어야함
		if (spcart.cartList.containsKey(product)) {
			// 카트리스트 해쉬맵에서 프로덕트 키가지고있는지 확인후 있으면 밸류값만 증가, 없으면 키와 밸류값 입력
			spcart.cartList.put(product, spcart.cartList.get(product) + quantity);
		} else {
			spcart.cartList.put(product, quantity);
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deleteCart(@PathVariable("id") Long id, Principal principal) {
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();
		Long cartid = spcart.getId();
		this.spCartService.delete(cartid);
	}

	@RequestMapping("/sum/{id}")
	public long sum(@PathVariable("id") Long id, Principal principal) {
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();
		long sum = 0;
		// 람다식 안에는 모두 상수만 들어가야함
		// spcart.cartList.forEach((containsKey, continsValue) -> {
		// long a = (containsKey.getProduct_price()) * continsValue;
		// sum = sum + a;
		// });
		// for (Set<Entry<Product, Long>> entry : spcart.cartList.entrySet()) {
		// String key = entry.getKey();
		// String value = entry.getValue();
		// }
		for (Product product : spcart.cartList.keySet()) {
			Long quantity = spcart.cartList.get(product);
			Long price = product.getProduct_price();
			sum += quantity * price;
		}
		return sum;
	}

}
