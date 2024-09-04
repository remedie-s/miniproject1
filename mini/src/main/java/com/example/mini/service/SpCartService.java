package com.example.mini.service;

import java.util.List;
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

	public void delete(SpCart cart) {
		this.spCartRepository.delete(cart);
	}

	public void save(SpCart spCart) {
		System.out.println("11111"+spCart);

		this.spCartRepository.save(spCart);
	}

	public SpCart selectOneCart(Long id) {
		Optional<SpCart> spCart = this.spCartRepository.findById(id);
		if (spCart.isPresent()) {
			return spCart.get();
		}
		throw new DataNotFoundException("cart not found");
	}

    public List <SpCart> findByUserid(Long userid) {
		List <SpCart> carts = this.spCartRepository.findByUserid(userid);
        if(carts.isEmpty()){
			throw new DataNotFoundException("cart 가 없어요");
		}
        return carts;
    }

}
