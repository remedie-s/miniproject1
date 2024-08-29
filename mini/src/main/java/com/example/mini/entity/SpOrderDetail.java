package com.example.mini.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.mini.config.OrderStatus;

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
public class SpOrderDetail {
	
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		@OneToOne
		private SpOrder spOrder;
		@OneToOne
		private SpAddress spaddress;
		private OrderStatus status;
		@OneToOne
		private Product product;
		private String request;
		
		
		
		

	

}
