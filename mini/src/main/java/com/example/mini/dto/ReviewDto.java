package com.example.mini.dto;

import com.example.mini.entity.Product;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReviewDto {
    private Long id;
    private Product product;
    private String content;
    private Long userid;
}
