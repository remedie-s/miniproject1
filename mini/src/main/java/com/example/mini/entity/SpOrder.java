package com.example.mini.entity;

import java.util.HashMap;

import com.example.mini.config.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	private Integer id;
	public HashMap<Product, Long> orderList;
	private OrderStatus status = OrderStatus.READY;
	private Boolean request = false;
	@ManyToOne
	private SpUser spuser;

}
