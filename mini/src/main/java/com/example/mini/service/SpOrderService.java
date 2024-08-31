package com.example.mini.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mini.entity.SpOrder;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.SpOrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SpOrderService {
    private final SpOrderRepository spOrderRepository;

    public SpOrder getOneOrder(Long id) {
        Optional<SpOrder> sporder = this.spOrderRepository.findById(id);
        if (sporder.isPresent()) {
            return sporder.get();
        }
        throw new DataNotFoundException("order not found");
    }

}
