package com.example.mini.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class CartDetail {
	
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		@ManyToOne
		private Product product_id;
		private Integer quantity;
		@ManyToOne
		private Cart cart;
		
		
}
