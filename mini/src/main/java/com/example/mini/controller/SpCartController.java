package com.example.mini.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.dto.CartListDto;
import com.example.mini.dto.SpCartForm;
import com.example.mini.entity.SpCart;
import com.example.mini.entity.SpUser;
import com.example.mini.service.ProductService;
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
	private final ProductService productService;

	@GetMapping("/list")
	public String list(Model model, Principal principal) {
		// PathVariable 있어야할까?
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		long id = user.getId();
		// 키값하고 밸류값 따로 ArrayList로 보내는게 나을까?

		return "redirect:/cart/list/" + id;
	}

	@GetMapping("/list/{id}")
	public String list(@PathVariable("id") Long id, Model model,
			Principal principal) {
		// PathVariable 있어야할까?
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		Long userid = user.getId();
		List<SpCart> spcart;		
		try {
			spcart = this.spCartService.findByUserid(userid);
			if(spcart==null){
				spcart = new ArrayList<>();
			}
		} catch (Exception e) {
			spcart = new ArrayList<>();
			e.printStackTrace();
			return "index";
		}
			ArrayList <CartListDto> cartlist = new ArrayList<>();
			long cartsum = 0;
		for (SpCart spCart : spcart) {
			CartListDto cartListDto = new CartListDto();
			cartListDto.setId(spCart.getProductid());
			cartListDto.setImage_url(this.productService.selectOneProduct(spCart.getProductid()).getImage_url());
			cartListDto.setProduct_name(this.productService.selectOneProduct(spCart.getProductid()).getProduct_name());
			cartListDto.setProduct_price(this.productService.selectOneProduct(spCart.getProductid()).getProduct_price());
			cartListDto.setQuantity(spCart.getQuantity());
			cartListDto.setSubtotal((this.productService.selectOneProduct(spCart.getProductid()).getProduct_price())*(spCart.getQuantity()));
			cartlist.add(cartListDto);
			Long quantity = spCart.getQuantity();
       		Long price = this.productService.selectOneProduct(spCart.getProductid()).getProduct_price();
        	cartsum += quantity * price;
		}

		model.addAttribute("cartsum",cartsum);
		model.addAttribute("cartlist", cartlist);
		return "cart_list";
	}
	
	@PostMapping("/add")
	public String addCart(@Valid SpCartForm spCartForm, BindingResult bindingResult,
			Model model, Principal principal) {
		if(bindingResult.hasErrors()){
			return "product_list";
		}
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		Long userid = user.getId();

		List<SpCart> spcart;
		
		try {
			spcart = this.spCartService.findByUserid(userid);
			if(spcart==null){
				spcart = new ArrayList<>();
			}
		} catch (Exception e) {
			spcart = new ArrayList<>();
			e.printStackTrace();
		}
		
		Long productid = spCartForm.getProductid();
		Long quantity = spCartForm.getQuantity();
		// 브라우저에서 정상적으로 넘오는지 확인
		System.out.println(productid);
		System.out.println(quantity);
		if (spcart.isEmpty()) {
			System.out.println("카트값 널인지 확인");
			SpCart cart = new SpCart();
			cart.setUserid(userid);
			cart.setProductid(productid);
			cart.setQuantity(quantity);
			cart.setCreate_date(LocalDateTime.now());
			this.spCartService.save(cart);
			return "redirect:/cart/list/" + userid;
		}
		System.out.println("카트리스트 카트리스트에 물건 양 등록");
		System.out.println("프로덕트 값이 있나 확인");
		boolean productispresent = false;
		if(!spcart.isEmpty()){
			for (SpCart spCarts : spcart){
				if (spCarts.getProductid().equals(productid)) {
					// 프러덕트 아이디를 받아와서 확인
					spCarts.setQuantity(spCarts.getQuantity() + quantity);
					productispresent = true;
					System.out.println("물건이 있음");
					this.spCartService.save(spCarts);
					return "redirect:/cart/list/" + userid;
				}
			}
		}
		if (!productispresent) {
			System.out.println("물건이 없음");
			SpCart spcart1=new SpCart();
			spcart1.setUserid(userid);
			spcart1.setProductid(productid);
			spcart1.setQuantity(quantity);
			spcart1.setCreate_date(LocalDateTime.now());
			System.out.println("물건이 없을때 넣는값"+spcart1 );
			this.spCartService.save(spcart1);	
		}
		
		
		return "redirect:/cart/list/" + userid;
	}

	@GetMapping("/delete/{id}")
	public String deleteCart(@PathVariable("id") Long id, Principal principal) {
		SpCart spCart = this.spCartService.selectOneCart(id);
		this.spCartService.delete(spCart);
		System.out.println("카트 물품 삭제 성공");

		return "redirect:/cart/list";
	}

	@RequestMapping("/sum")
	public void sum(Principal principal, Model model) {
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		Long userid=user.getId();
		List<SpCart> cartlist = this.spCartService.findByUserid(userid);
		long sum = 0;
		
		if (cartlist != null) {
			for (SpCart spcart : cartlist) {
				Long quantity = spcart.getQuantity();
				Long price= this.productService.selectOneProduct(spcart.getProductid()).getProduct_price();
				// 프로덕트 아이디를 받아와서 프로덕트를 가져온다음  거기서 가격을 뽑아야함
				sum += quantity * price;
			}
		}
		model.addAttribute("cartSum", sum);

	}

}
