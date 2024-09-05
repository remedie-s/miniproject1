package com.example.mini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProduct_Id(Long productId);

}

