package com.example.mini.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductForm {
	@Transient
	private Integer id;

	@Column(length = 50)
	@NotEmpty(message = "물품 이름은 비어선 안됩니다.")
	private String product_name;
	@Column(length = 300)
	@NotEmpty(message = "물품 설명은 비어선 안됩니다.")
	private String description;
	//가격 1~1,000,000 
	@Min(1) 
	@Max(1_000_000)
	private Long product_price;
	//수량 1~1000000
	@Min(1) 
	@Max(1_000_000)
	private Long product_quantity;
	@NotEmpty(message = "이미지 주소는 비어선 안됩니다.")
	private String image_url;

	public ProductForm(String product_name, String description, Long product_price, Long product_quantity,
			String image_url) {
		super();
		this.product_name = product_name;
		this.description = description;
		this.product_price = product_price;
		this.product_quantity = product_quantity;
		this.image_url = image_url;
	}

}
