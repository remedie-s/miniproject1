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
import jakarta.persistence.OneToOne;
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
	private Integer id;
	
	@Column(length = 50, unique = true)
	private String spuser_name;
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
	@OneToOne(mappedBy = "spuser", cascade = CascadeType.REMOVE)
	private SpCart spcart;
	
	@JsonIgnore
	@OneToMany
	private List <SpAddress> addressList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "spuser", cascade = CascadeType.REMOVE)
	private List <SpOrder> ordersList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "spuser", cascade = CascadeType.REMOVE)
	private List <Review> reviewList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "spuser", cascade = CascadeType.REMOVE)
	private List <QnA_Post> qnA_PostList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "spuser", cascade = CascadeType.REMOVE)
	private List <QnA_Review> qnA_reviewList;
	
	
	
	

}
