package com.example.mini.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.entity.Product;
import com.example.mini.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class ProductController {
	private final ProductService productService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Product> products=this.productService.getAllProduct();
		model.addAttribute("products", products);
		return "product_list";
	}
	
	
	

}
