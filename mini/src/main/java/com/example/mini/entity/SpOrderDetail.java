package com.example.mini.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	private Long id;
    private Long productid;
    private Long quantity;
    @ManyToOne
    private SpOrder spOrder;

}
