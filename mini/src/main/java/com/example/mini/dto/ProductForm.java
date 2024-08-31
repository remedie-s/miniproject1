package com.example.mini.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
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
	@NotEmpty(message = "가격은 비어선 안됩니다.")
	private Long product_price;
	@NotEmpty(message = "수량은 비어선 안됩니다.")
	private String product_quantity;
	@NotEmpty(message = "이미지 주소는 비어선 안됩니다.")
	private String image_url;

	public ProductForm(String product_name, String description, Long product_price, String product_quantity,
			String image_url) {
		super();
		this.product_name = product_name;
		this.description = description;
		this.product_price = product_price;
		this.product_quantity = product_quantity;
		this.image_url = image_url;
	}

}
