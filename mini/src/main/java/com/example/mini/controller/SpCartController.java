package com.example.mini.controller;

import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.dto.SpCartForm;
import com.example.mini.entity.Product;
import com.example.mini.entity.SpCart;
import com.example.mini.entity.SpCartDetail;
import com.example.mini.entity.SpUser;
import com.example.mini.service.SpCartService;
import com.example.mini.service.SpUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/cart")
@Controller
@RequiredArgsConstructor
public class SpCartController implements Serializable {
	private final SpCartService spCartService;
	private final SpUserService spUserService;

	@GetMapping("/list")
	public String list(@Valid SpCartForm spCartForm, BindingResult bindingResult, Model model,
			Principal principal) {
		// PathVariable 있어야할까?
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();
		long id = user.getId();
		model.addAttribute(spcart);
		// 키값하고 밸류값 따로 ArrayList로 보내는게 나을까?

		return "redirect:/cart/list/" + id;
	}

	@PostMapping("/list/{id}")
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

	@PostMapping("/add")
	public String addCart(@Valid SpCartForm spCartForm, BindingResult bindingResult,
			@ModelAttribute("product") Product product,
			@ModelAttribute("quantity") Long quantity,
			Model model, Principal principal) {
		// PathVariable 있어야할까?
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();
		if(spcart==null){
			System.out.println("카트값 널인지 확인");
			spcart = new SpCart();
			spcart.setSpuser(user);
			spcart.setCartlist(new ArrayList<SpCartDetail>());
			spcart.setCreate_date(LocalDateTime.now());
			this.spCartService.save(spcart);
		}
		System.out.println("카트리스트 카트리스트에 물건 양 등록");
		System.out.println("프로덕트 값이 있나 확인");
		boolean productispresent=false;
		for (SpCartDetail spCartDetail : spcart.getCartlist()) {
			if(spCartDetail.getProduct().equals(product)){
				spCartDetail.setQuantity(spCartDetail.getQuantity()+quantity);
			productispresent = true;
			break;
			}
		} 
		if(!productispresent){
		SpCartDetail cartDetail = new SpCartDetail();
		cartDetail.setProduct(product);
		cartDetail.setQuantity(quantity);
		cartDetail.setSpCart(spcart);
		spcart.getCartlist().add(cartDetail);}
		this.spCartService.save(spcart);
		
		return "redirect:/product/list";
	}

	@DeleteMapping("/delete")
	public String deleteCart(Principal principal) {
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();
		if(spcart != null){
		this.spCartService.delete(spcart.getId());}
		return "redirect:/product/list";
	}

	@RequestMapping("/sum")
	public long sum(Principal principal) {
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
	if(spcart != null){
		for (SpCartDetail detail : spcart.getCartlist()) {
			Long quantity = detail.getQuantity();
			Long price = detail.getProduct().getProduct_price();
			sum += quantity * price;
		}
	}
		return sum;
	}

}
