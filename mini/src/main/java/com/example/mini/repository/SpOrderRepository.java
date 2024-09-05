package com.example.mini.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.SpOrder;

public interface SpOrderRepository extends JpaRepository<SpOrder, Long> {

    List<SpOrder> findByUserid(Long userid);

    Page<SpOrder> findByUserid(Long userid, Pageable pageable);
}
