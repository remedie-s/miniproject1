package com.example.mini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}

