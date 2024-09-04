package com.example.mini.entity;

import java.time.LocalDateTime;

import com.example.mini.config.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class SpOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private OrderStatus status = OrderStatus.READY;
	private Boolean request = false;
	private Long userid;
	private Long productid;
    private Long quantity;
	private LocalDateTime create_time;

}
