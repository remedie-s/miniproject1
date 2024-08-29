package com.example.mini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mini.entity.Product;
import com.example.mini.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ProductService {
	private final ProductRepository productRepository;

	public List<Product> getAllProduct() {

		return this.productRepository.findAll();
	}
	

}
