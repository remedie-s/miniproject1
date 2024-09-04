package com.example.mini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.SpCart;

public interface SpCartRepository extends JpaRepository<SpCart, Long> {

    List<SpCart> findByUserid(Long userid);

}
