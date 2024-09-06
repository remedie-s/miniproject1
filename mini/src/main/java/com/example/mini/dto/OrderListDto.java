package com.example.mini.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderListDto {
    private Long id;
    private Integer status;
    private Integer request;
    private String image_url;
    private String product_name;
    private Long product_price;
    private Long quantity;
    private Long subtotal;
    private LocalDateTime createTime;
    private Long orderid;
    private Long userid;

}
