package com.example.mini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.SpOrder;

public interface SpOrderRepository extends JpaRepository<SpOrder, Long> {

}
