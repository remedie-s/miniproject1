package com.example.mini.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class OrderDetail {
	
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
	
		@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
		private Cart cart;
		@OneToOne
		private Address address_id;
		private OrderStatus status;
		private String request;
		
		
		
		

	

}
