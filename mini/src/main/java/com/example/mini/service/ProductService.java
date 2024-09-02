package com.example.mini.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mini.entity.Product;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	private final ProductRepository productRepository;

	public List<Product> getAllProduct() {

		return this.productRepository.findAll();
	}

	public Product create(String product_name, String description, Long product_price, Long product_quantity,
			String image_url) {
		Product p = new Product();
		p.setProduct_name(product_name);
		p.setDescription(description);
		p.setProduct_price(product_price);
		p.setProduct_quantity(product_quantity);
		p.setImage_url(image_url);
		p.setCreate_date(LocalDateTime.now());
		this.productRepository.save(p);
		return p;
	}

	public Product selectOneProduct(long id) {
		Optional<Product> product = this.productRepository.findById(id);
		if (product.isPresent()) {
			return product.get();
		}
		throw new DataNotFoundException("product not found");
	}

	public void delete(Product product) {
		this.productRepository.delete(product);
	}

	public void modify(Product product) {
		this.productRepository.save(product);
	}

}
