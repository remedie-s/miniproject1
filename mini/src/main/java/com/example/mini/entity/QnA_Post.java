package com.example.mini.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
public class QnA_Post {
	
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		@ManyToOne
		private User user_id;
		@Column(length = 100)
		private String subject;
		@Column(length = 500)
		private String content;
		private LocalDateTime create_date;
		@JsonIgnore
		@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
		private List <QnA_Review> reviewsList;
		
		
		

	

}
