package com.example.mini.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mini.entity.Product;
import com.example.mini.entity.Review;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;


    public void create(Product product, String content, Long userid) {
		Review review = new Review();
		review.setContent(content);
		review.setCreate_date(LocalDateTime.now());	// 서버측의 시간
		review.setProduct(product);
        review.setUserid(userid);
		this.reviewRepository.save(review);
	}
    
    public Review selectOneReview(Long id) {
		// 실습 2분 => getOnePost와 패턴 동일함
		Optional<Review> review = this.reviewRepository.findById(id);
		if(review.isPresent()) {
			return review.get();
		}
		// 커스텀 예외 상황
		throw new DataNotFoundException("물건이 없어요");
	}
    public void delete(Review review) {
		this.reviewRepository.delete(review);
	}

	public void modify(Review review) {
		this.reviewRepository.save(review);
	}

    public List<Review> findByProductid(Long id) {
		return this.reviewRepository.findByProduct_Id(id);
        
    }

}
