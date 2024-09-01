package com.example.mini.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.config.OrderStatus;
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
	public String orderList(Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		long id = user.getId();
		return "redirect:/order/list/" + id;
	}

	@GetMapping("/list/{id}")
	public String orderList(Model model, @PathVariable("id") Long id, Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		List<SpOrder> ordersList = user.getOrdersList();
		model.addAttribute("orderList", ordersList);

		return "order_list";
	}

	@GetMapping("/detail/{id}")
	public String orderdetail(Model model, @PathVariable("id") Long id, SpOrderForm spOrderForm) {
		SpOrder order = this.spOrderService.getOneOrder(id);
		// model.addAttribute("order", order);
		return "order_detail";
	}

	// 장바구니 있는지 탐색후 있으면 추가 없으면 생성
	@GetMapping("/create")
	public String create(SpOrderForm spOrderForm, Model model,
			Principal principal) {
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		SpCart spcart = user.getSpcart();
		// 카트 사이즈확인(비어있는지 확인 사이즈)
		if (spcart.getCartList().size() >= 1) {
			// model.addAttribute("method", "post");
			return "order_form";
		}
		// 카트 비었을 때 카트가 비었다고 메시지?
		else {
			return "cart_empty";
		}
	}

	@PostMapping("/create")
	public String create(@Valid SpOrderForm spOrderForm, BindingResult bindingResult, Model model,
			Principal principal) {
		SpOrder spOrder = new SpOrder();
		String name = principal.getName();
		SpUser user = spUserService.findbyUsername(name);
		SpCart cart = user.getSpcart();
		// 카트에서 카트리스트 가져온후 오더에 있는 리스트에 복사
		HashMap<Product, Long> cartList = cart.getCartList();
		spOrder.setOrderList(cartList);
		System.out.println("주문생성");
		cart.cartList.clear();
		return "order_list";
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
		} else {
			System.out.println("권한이 없습니다");
		}
		// 구매자에게 리뷰권한 줘야함
		// 오더에서 키값(물품)하고 유저아이디 뽑아서 productbuylist 에 추가하면 될듯?
	}

}
