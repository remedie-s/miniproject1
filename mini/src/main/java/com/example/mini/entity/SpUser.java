package com.example.mini.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.mini.config.SpUserGrade;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SpUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, unique = true)
	private String username;
	@Column(length = 108)
	private String password;
	@Column(length = 10)
	private String first_name;
	@Column(length = 10)
	private String last_name;
	@Column(length = 36)
	private String phone_number;
	@Column(unique = true)
	private String e_mail;
	
	private LocalDateTime create_date;
	private SpUserGrade spuser_grade;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "spuser", cascade = CascadeType.REMOVE)
	private List <SpAddress> addressList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "spuser", cascade = CascadeType.REMOVE)
	private List <QnA_Post> qnA_PostList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "spuser", cascade = CascadeType.REMOVE)
	private List <QnA_Review> qnA_reviewList;
	
	
	
	

}
