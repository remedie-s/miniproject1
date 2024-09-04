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

import com.example.mini.config.OrderStatus;
import com.example.mini.dto.OrderListDto;
import com.example.mini.dto.SpCartForm;
import com.example.mini.dto.SpOrderForm;
import com.example.mini.entity.Product;
import com.example.mini.entity.SpCart;
import com.example.mini.entity.SpOrder;
import com.example.mini.entity.SpUser;
import com.example.mini.service.ProductService;
import com.example.mini.service.SpCartService;
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
	private final ProductService productService;
	private final SpCartService spCartService;

	@GetMapping("/list")
	public String orderList(Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		long id = user.getId();
		return "redirect:/order/list/" + id;
	}

	@GetMapping("/list/{id}")
	public String orderList(@PathVariable("id") Long id, Model model, Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		Long userid = user.getId();
		if (principal.getName().equals("seller") || principal.getName().equals("admin")) {
			return "order_seller_list";
		}
		List<SpOrder> spOrder;
		try {
			spOrder = this.spOrderService.findByUserid(userid);
			if (spOrder == null) {
				spOrder = new ArrayList<>();
			}
		} catch (Exception e) {
			spOrder = new ArrayList<>();
			e.printStackTrace();
			return "index";
		}
		ArrayList<OrderListDto> orderlist = new ArrayList<>();
		long ordersum = 0;
		for (SpOrder sporder : spOrder) {
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setId(sporder.getProductid());
			orderListDto.setImage_url(this.productService.selectOneProduct(sporder.getProductid()).getImage_url());
			orderListDto
					.setProduct_name(this.productService.selectOneProduct(sporder.getProductid()).getProduct_name());
			orderListDto
					.setProduct_price(this.productService.selectOneProduct(sporder.getProductid()).getProduct_price());
			orderListDto.setQuantity(sporder.getQuantity());
			orderListDto.setSubtotal((this.productService.selectOneProduct(sporder.getProductid()).getProduct_price())
					* (sporder.getQuantity()));
			orderListDto.setCreate_time(LocalDateTime.now());
			orderlist.add(orderListDto);
			Long quantity = sporder.getQuantity();
			Long price = this.productService.selectOneProduct(sporder.getProductid()).getProduct_price();
			ordersum += quantity * price;
		}
		model.addAttribute("ordersum", ordersum);
		model.addAttribute("orderlist", orderlist);

		return "order_list";
	}

	@GetMapping("/detail/{id}")
	public String orderdetail(Model model, @PathVariable("id") Long id, SpOrderForm spOrderForm) {
		SpOrder order = this.spOrderService.getOneOrder(id);
		model.addAttribute("order", order);
		return "order_detail";
	}

	// 장바구니 있는지 탐색후 있으면 추가 없으면 홈페이지
	@GetMapping("/create")
	public String create(SpOrderForm spOrderForm, SpCartForm spCartForm, Model model,
			Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		Long userid = user.getId();

		List<SpCart> cartlist = this.spCartService.findByUserid(userid);

		// 카트 확인(비어있는지 확인)
		if (cartlist.isEmpty()) {
			System.out.println("카트가 비어있어요");
			return "product_list";
		} else {
			System.out.println("카트가 들어있습니다.");
			return "order_form";
		}
	}

	// TODO 고쳐야함
	@PostMapping("/create")
	public String create(@Valid SpOrderForm spOrderForm, BindingResult bindingResult, SpCartForm spCartForm,
			Model model, Principal principal) {
		if (bindingResult.hasErrors()) {
			System.out.println("에러가 있어요");
			return "product_list";
		}
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		Long userid = user.getId();
		List<SpCart> cartlist = this.spCartService.findByUserid(userid);
		if (cartlist.isEmpty()) {
			System.out.println("카트가 비어있어요");
			return "product_list";
		}
		System.out.println("카트가 들어있습니다.");
		// 카트에서 카트리스트 가져온후 오더에 있는 리스트에 복사
		for (SpCart spCart : cartlist) {
			SpOrder spOrder = new SpOrder();
			spOrder.setUserid(userid);
			spOrder.setProductid(spCart.getProductid());
			spOrder.setQuantity(spCart.getQuantity());
			spOrder.setCreate_time(LocalDateTime.now());
			spOrder.setStatus(OrderStatus.READY);
			spOrder.setRequest(false);
			this.spOrderService.save(spOrder);
			System.out.println("주문생성 완료1");
		}
		System.out.println("주문생성 완료2");
		System.out.println("카트 삭제");
		this.spCartService.deleteByUserid(userid);

		return "order_list";
	}

	@GetMapping("/seller/list")
	public String orderSellerList(Principal principal, Model model) {
		if (principal.getName().equals("seller") || principal.getName().equals("admin")) {
			List<SpOrder> order = this.spOrderService.getAllOrder();
			model.addAttribute("order", order);

		} else {
			System.out.println("권한이 없습니다");

		}
		return "order_seller_list";
	}

	@PostMapping("/accept")
	public void accept(SpOrderForm spOrderForm, Model model, Principal principal) {
		// 아이디가 셀러 혹은 어드민일 경우에만 주문상태 변경가능

		if (principal.getName().equals("seller") || principal.getName().equals("admin")) {
			// 모델로 받는게 나을려나
			Long id = spOrderForm.getId();
			SpOrder order = spOrderService.getOneOrder(id);
			order.setRequest(true);
			order.setStatus(OrderStatus.START);
			this.spOrderService.save(order);
		} else {
			System.out.println("권한이 없습니다");

		}
	}

	@PostMapping("/arrive")
	public void arrive(SpOrderForm spOrderForm, Model model, Principal principal) {
		if (principal.getName().equals("seller") || principal.getName().equals("admin")) {
			Long id = spOrderForm.getId();
			SpOrder order = spOrderService.getOneOrder(id);
			order.setStatus(OrderStatus.ARRIVE);
			this.spOrderService.save(order);
		} else {
			System.out.println("권한이 없습니다");

		}
	}

	@PostMapping("/end")
	public void end(SpOrderForm spOrderForm, Model model, Principal principal) {
		if (principal.getName().equals("seller") || principal.getName().equals("admin")) {
			Long id = spOrderForm.getId();
			SpOrder order = spOrderService.getOneOrder(id);
			order.setStatus(OrderStatus.END);
			SpUser user = this.spUserService.findbyId(order.getUserid());
			Long userid = user.getId();
			Long productid = order.getProductid();
			Product product = this.productService.selectOneProduct(productid);
			product.addCustomer(userid);
			this.productService.save(product);
		} else {
			System.out.println("권한이 없습니다");
		}
	}

}
