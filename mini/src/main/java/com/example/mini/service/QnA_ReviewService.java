package com.example.mini.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mini.entity.QnA_Post;
import com.example.mini.entity.QnA_Review;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.QnA_ReviewRepository;

@Service
public class QnA_ReviewService {
    @Autowired
    private QnA_ReviewRepository qnA_ReviewRepository;

    public void create(QnA_Post post, String content) {
        QnA_Review review = new QnA_Review();
        review.setContent(content);
        review.setCreate_date(LocalDateTime.now());
        review.setQnA_Post(post);
        this.qnA_ReviewRepository.save(review);
    }

    public QnA_Review selectOneReview(Integer id) {
        Optional<QnA_Review> review = this.qnA_ReviewRepository.findById(id);
        if (review.isPresent()) {
            return review.get();
        }
        throw new DataNotFoundException("post not found");
    }

    public void delete(QnA_Review review) {
        this.qnA_ReviewRepository.delete(review);
    }

    public void modify(QnA_Review review) {
        this.qnA_ReviewRepository.save(review);
    }

}
