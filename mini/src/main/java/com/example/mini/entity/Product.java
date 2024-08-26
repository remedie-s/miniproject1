package com.example.mini.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50)
	private String product_name;
	@Column(length = 300)
	private String description;
	private Integer product_price;
	private String product_quantity;
	private String image_url; 
	@OneToMany
	private List<Review> reviewList;// TODO 3:  추후 수정(리뷰 배열)
	private LocalDateTime create_date;
	
	
	

}
