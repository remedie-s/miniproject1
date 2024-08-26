package com.example.mini.entity;

import java.time.LocalDateTime;
import java.util.List;

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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50, unique = true)
	private String user_name;
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
	@OneToMany
	private List <Address> addressList;// TODO 1:  추후 수정(주소 배열)
	private LocalDateTime create_date;
	private UserGrade user_grade;// TODO 2:  추후 수정(grade enum)
	
	public User(String user_name, String password, String first_name, String last_name, String phone_number,
			String e_mail, UserGrade user_grade) {
		super();
		this.user_name = user_name;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone_number = phone_number;
		this.e_mail = e_mail;
		this.user_grade = user_grade;
	}
	
	

}
