package com.example.mini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.dto.QnAReveiwForm;
import com.example.mini.entity.QnA_Post;
import com.example.mini.entity.QnA_Review;
import com.example.mini.service.QnA_PostService;
import com.example.mini.service.QnA_ReviewService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/qna/review")
public class QnA_ReviewController {
    @Autowired
    private QnA_ReviewService reviewService;

    @Autowired
    private QnA_PostService qnA_PostService;

    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable("id") Integer id,
            @Valid QnAReveiwForm reviewForm, BindingResult bindingResult) {
        QnA_Post post = this.qnA_PostService.getOnePost(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "qna_detail";
        }
        this.reviewService.create(post, reviewForm.getContent());
        return "redirect:/qnapost/detail/" + id;
    }

    /**
     * 
     * @param id : 리뷰 ID
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        // 리뷰 ID => 리뷰 엔티티 획득
        QnA_Review review = this.reviewService.selectOneReview(id);
        this.reviewService.delete(review);
        // Post ID를 획득해서 해당 Post의 상세페이지로 이동
        return "redirect:/qna/post/detail/" + review.getQnA_Post().getId();
    }

    @GetMapping("/modify/{id}")
    public String modify(QnAReveiwForm reviewForm, @PathVariable("id") Integer id) {
        // 실습 1분
        // Review 내용 획득
        QnA_Review review = this.reviewService.selectOneReview(id);
        // reviewForm에 내용 설정
        reviewForm.setContent(review.getContent());
        return "qna_review_form";
    }

    @PostMapping("/modify/{id}")
    public String modify(@Valid QnAReveiwForm reviewForm, BindingResult bindingResult,
            @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "qna_review_form";
        }
        QnA_Review review = this.reviewService.selectOneReview(id);
        review.setContent(reviewForm.getContent());
        this.reviewService.modify(review);
        return "redirect:/post/detail/" + review.getQnA_Post().getId();
    }
}
