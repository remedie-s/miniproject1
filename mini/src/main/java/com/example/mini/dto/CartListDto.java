package com.example.mini.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartListDto {

    private Long id;
    private String image_url;
    private String product_name;
    private Long product_price;
    private Long quantity;
    private Long subtotal;
}
