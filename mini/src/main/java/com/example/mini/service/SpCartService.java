package com.example.mini.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mini.entity.SpCart;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.SpCartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpCartService {

	private final SpCartRepository spCartRepository;

	public SpCart findbyId(Long cartId) {
		Optional<SpCart> spcart = this.spCartRepository.findById(cartId);
		if (spcart.isPresent()) {
			return spcart.get();
		}
		throw new DataNotFoundException("user not found");
	}

	public void delete(Long id) {
		this.spCartRepository.deleteById(id);
	}

}
