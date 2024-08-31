package com.example.mini.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.dto.ProductForm;
import com.example.mini.entity.Product;
import com.example.mini.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class ProductController {
	private final ProductService productService;

	@GetMapping("/list")
	public String list(Model model) {
		List<Product> products = this.productService.getAllProduct();
		model.addAttribute("products", products);
		return "product_list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id, ProductForm productForm) {
		Product product = this.productService.selectOneProduct(id);
		model.addAttribute("product", product);
		return "product_detail";

	}

	@GetMapping("/create")
	public String create(Model model, ProductForm productform) {
		model.addAttribute("method", "post");
		return "product_form";
	}

	@PostMapping("/create")
	public String create(@Valid ProductForm productForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "product_form";
		}
		this.productService.create(productForm.getProduct_name(), productForm.getDescription(),
				productForm.getProduct_price(), productForm.getProduct_quantity(), productForm.getImage_url());
		return "redirect:/product/detail";
		// 상품 디테일 페이지로 가게 해야되나?
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

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id) {
		Product product = this.productService.selectOneProduct(id);
		this.productService.delete(product);
		return "redirect:/product/list";
	}

}
