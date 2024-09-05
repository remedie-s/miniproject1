package com.example.mini.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.dto.ProductForm;
import com.example.mini.dto.ReviewForm;
import com.example.mini.dto.SpCartForm;
import com.example.mini.entity.Product;
import com.example.mini.entity.Review;
import com.example.mini.service.ProductService;
import com.example.mini.service.ReviewService;
import com.example.mini.service.SpUserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class ProductController {
	private final ProductService productService;
	private final SpUserService spUserService;
	private final ReviewService	reviewService;

	@GetMapping("/list")
	public String list(Model model) {
		List<Product> productslist = this.productService.getAllProduct();
		model.addAttribute("productslist", productslist);
		// for (Product product : productslist) {
		// System.out.println(product);
		// }
		return "product_list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id, ProductForm productForm, SpCartForm spCartForm) {
		ReviewForm reviewForm = new ReviewForm();
		Product product = this.productService.selectOneProduct(id);
		List<Review> reviewList = this.reviewService.findByProductid(id);
		
		model.addAttribute("product", product);
		model.addAttribute("reviewlist",reviewList);
		return "product_detail";

	}

	@GetMapping("/create")
	public String create(ProductForm productform, Principal principal, Model model) {
		String name = principal.getName();
		model.addAttribute("name", name);
		if (name.equals("seller") || name.equals("admin")) {
			return "product_form";
		} else {
			System.out.println("권한이 없는 사용자입니다.");
			return "index";
		}
	}

	@PostMapping("/create")
	public String create(@Valid ProductForm productForm, BindingResult bindingResult, HttpServletResponse response) {
		System.out.println("물품등록");
		if (bindingResult.hasErrors()) {
			bindingResult.getErrorCount();
			System.out.println("에러발생");
			return "product_form";
		}
		Product product;

		product = this.productService.create(productForm.getProduct_name(), productForm.getDescription(),
				productForm.getProduct_price(), productForm.getProduct_quantity(), productForm.getImage_url());
		System.out.println("물품등록 완료");

		return "redirect:/product/list";
		// 상품 디테일 페이지로 가게 해야되나?
		// TODO 물건 카테고리 추가요망
	}

	@GetMapping("/modify/{id}")
	public String modify(Model model, ProductForm productForm, @PathVariable("id") long id) {
		Product product = this.productService.selectOneProduct(id);
		productForm.setProduct_name(product.getProduct_name());
		productForm.setDescription(product.getDescription());
		productForm.setProduct_price(product.getProduct_price());
		productForm.setProduct_quantity(product.getProduct_quantity());
		productForm.setImage_url(product.getImage_url());
		model.addAttribute("method", "put");

		return "product_form";
	}

	@PutMapping("/modify/{id}")
	public String modify(@Valid ProductForm productForm, BindingResult bindingResult,
			@PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "prodcut_form";
		}
		Product product = this.productService.selectOneProduct(id);

		product.setProduct_name(productForm.getProduct_name());
		product.setDescription(productForm.getDescription());
		product.setProduct_price(productForm.getProduct_price());
		product.setProduct_quantity(productForm.getProduct_quantity());
		product.setImage_url(productForm.getImage_url());

		this.productService.modify(product);

		return "redirect:/product/detail/" + id;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id, Principal principal) {
		if (principal.getName().equals("admin") || principal.getName().equals("seller")) {
			Product product = this.productService.selectOneProduct(id);
			this.productService.delete(product);
			System.out.println("삭제 성공");
			return "redirect:/product/list";
		}
		System.out.println("권한없는 사용자입니다");
		return "redirect:/product/list";
	}
	@GetMapping("/review/create/{id}")
	public String reviewCreate(@PathVariable("id") long id, Principal principal, Model model){
		Product product = this.productService.selectOneProduct(id);
		ReviewForm reviewForm = new ReviewForm();
		reviewForm.setProductid(id);
		model.addAttribute("product", product);
		model.addAttribute("reviewForm", reviewForm);
		return "product_review_form";
	}
	@PostMapping("/review/create/{id}")
	public String reviewCreate(Model model, @Valid ReviewForm reviewForm, BindingResult bindingResult, Principal principal){
		String name = principal.getName();
		Long userid = this.spUserService.findbyUsername(name).getId();
		Long productid=reviewForm.getProductid();
		Product product = this.productService.selectOneProduct(productid);
		Long id=product.getId();
		List<Long> costomerList = product.getCostomerList();
		System.out.println(costomerList);
		for (Long costomerid : costomerList) {
			if(userid==costomerid){
				if(bindingResult.hasErrors()){
					System.out.println("리뷰 쓰기 중 에러가 있어요!");
					return "product_list";
				}
				this.reviewService.create(product,reviewForm.getContents(), userid);
				System.out.println("리뷰 등록 완료");
				return "redirect:/product/detail/"+id;
			}
			
		}
		System.out.println("리뷰쓰기 권한이 없어요!");
		return "product_list";
	}
	@GetMapping("/review/delete/{id}")
	public String reviewDelete(Model model, @PathVariable("id") Long id, Principal principal) {
		String name = principal.getName();
		Long userid = this.spUserService.findbyUsername(name).getId();
		Review review = this.reviewService.selectOneReview(id);
		long productid=review.getProduct().getId();
		if(review.getUserid()==userid || name.equals("admin") || name.equals("seller")){
			this.reviewService.delete(review);
			System.out.println("리뷰 삭제 완료");
		}
		else{
		System.out.println("리뷰쓰기 삭제 권한이 없어요!");}
		return "redirect:/product/detail/"+productid;
	}
	
}
