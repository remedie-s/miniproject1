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
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Review {
	
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		@ManyToOne//TODO 4 : 외래키 작성
		private Product product_id;
		@ManyToOne
		private User user_id;
		@Column(length = 100)
		private String subject;
		@Column(length = 500)
		private String content;
		
		
		private LocalDateTime create_date;
		
		
		

	

}
