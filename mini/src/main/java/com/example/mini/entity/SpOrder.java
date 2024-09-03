package com.example.mini.entity;


import java.time.LocalDateTime;
import java.util.List;

import com.example.mini.config.OrderStatus;

import jakarta.persistence.CascadeType;
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
public class SpOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private OrderStatus status = OrderStatus.READY;
	private Boolean request = false;
	@ManyToOne
	private SpUser spuser;
	@OneToMany(mappedBy="SpOrder", cascade=CascadeType.REMOVE)
	private List<SpOrderDetail> orderlist;
	private LocalDateTime create_time;

}
