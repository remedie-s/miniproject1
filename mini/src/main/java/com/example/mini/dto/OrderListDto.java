package com.example.mini.dto;

import java.time.LocalDateTime;

import com.example.mini.config.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderListDto {
    private Long id;
    private OrderStatus status = OrderStatus.READY;
    private Boolean request = false;
    private String image_url;
    private String product_name;
    private Long product_price;
    private Long quantity;
    private Long subtotal;
    private LocalDateTime create_time;
    private Long orderid;

}
