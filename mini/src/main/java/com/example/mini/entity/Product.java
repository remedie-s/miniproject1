package com.example.mini.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	private Long id;

	@Column(length = 50)
	private String product_name;
	@Column(length = 300)
	private String description;
	private Long product_price;
	private String product_quantity;
	private String image_url;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Review> reviewList;
	private LocalDateTime create_date;
	@OneToOne
	private SpCart Spcart;
	@OneToOne
	private SpOrder spOrder;

}
