package com.example.mini.dto;

import java.time.LocalDateTime;

import com.example.mini.entity.SpCart;
import com.example.mini.entity.SpOrderDetail;
import com.example.mini.entity.SpUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;

public class SpOrderForm {
	
	private Integer id;


}
