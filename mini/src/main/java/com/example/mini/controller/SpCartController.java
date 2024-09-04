package com.example.mini.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		SpCart spcart = user.getSpcart();
		if (spcart.getId() != id) {
			System.out.println("접근 권한이 없습니다.");
			return "index";
		}
		List<SpCartDetail> cartlist = spcart.getCartlist();
		model.addAttribute("cartlist", cartlist);
		// for (SpCartDetail spCartDetail : cartlist) {
		// spCartDetail.getId();
		// spCartDetail.getProduct().getImage_url();
		// spCartDetail.getProduct().getProduct_name();
		// spCartDetail.getProduct().getProduct_price();
		// spCartDetail.getQuantity();
		// }
		return "cart_list";
	}

	@PostMapping("/add")
	public String addCart(@Valid SpCartForm spCartForm, BindingResult bindingResult,
			@ModelAttribute("product") Product product,
			@ModelAttribute("quantity") Long quantity,
			Model model, Principal principal) {
		String name = principal.getName();
		SpUser user = this.spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();

		if (spcart == null) {
			System.out.println("카트값 널인지 확인");
			spcart = new SpCart();
			spcart.setSpuser(user);
			spcart.setCartlist(new ArrayList<SpCartDetail>());
			spcart.setCreate_date(LocalDateTime.now());
			this.spCartService.save(spcart);
		}
		// 브라우저에서 정상적으로 넘오는지 확인
		System.out.println(product);
		System.out.println(quantity);
		System.out.println("카트리스트 카트리스트에 물건 양 등록");
		System.out.println("프로덕트 값이 있나 확인");
		// 프로덕트가 카트디테일에 존재하는가?
		Long productid = product.getId();
		// 프로덕트 아이디 받아오기
		boolean productispresent = false;
		if(spCartDetail==null){break;}
		for (SpCartDetail spCartDetail : spcart.getCartlist()) {
			
			if (spCartDetail.getProductid().equals(productid)) {
				// 프러덕트 아이디를 받아와서 확인
				spCartDetail.setQuantity(spCartDetail.getQuantity() + quantity);
				productispresent = true;
				System.out.println("물건이 있음");
				break;
			}
		}

		if (!productispresent) {
			SpCartDetail cartDetail = new SpCartDetail();
			System.out.println("물건이 없음");
			cartDetail.setProductid(productid);
			cartDetail.setQuantity(quantity);
			cartDetail.setSpCart(spcart);

			System.out.println("카트디테일"+cartDetail );
			spcart.getCartlist().add(cartDetail);
		}

		this.spCartService.save(spcart);
		System.out.println(spcart);
		return "redirect:/product/list";
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
		if (spcart != null) {
			for (SpCartDetail detail : spcart.getCartlist()) {
				Long quantity = detail.getQuantity();
				Long price= this.productService.selectOneProduct(detail.getProductid()).getProduct_price();
				// 프로덕트 아이디를 받아와서 프로덕트를 가져온다음  거기서 가격을 뽑아야함
				sum += quantity * price;
			}
		}
		model.addAttribute("sum", sum);

	}

}
