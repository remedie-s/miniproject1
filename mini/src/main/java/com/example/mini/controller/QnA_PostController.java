package com.example.mini.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.dto.QnAPostForm;
import com.example.mini.dto.QnAReveiwForm;
import com.example.mini.entity.QnA_Post;
import com.example.mini.entity.SpUser;
import com.example.mini.service.QnA_PostService;
import com.example.mini.service.QnA_ReviewService;
import com.example.mini.service.SpUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna/post")
public class QnA_PostController {
	private final QnA_PostService qnA_PostService;
	private final QnA_ReviewService qnA_ReviewService;
	private final SpUserService spUserService;

	@GetMapping("/list")
	public String list(Model model) {
		List<QnA_Post> postlist = this.qnA_PostService.getAllPost();
		for (QnA_Post qnA_Post : postlist) {
			System.out.println(qnA_Post);
		}
		model.addAttribute("postlist", postlist);
		return "qna_post_list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id,
			QnAReveiwForm reviewForm, Principal principal) {
		QnA_Post post = this.qnA_PostService.getOnePost(id);
		String username = principal.getName();
		System.out.println(post.getReviewsList());
		model.addAttribute("post", post);
		model.addAttribute("username", username);
		return "qna_post_detail";
	}

	@GetMapping("/create")
	public String create(Model model, QnAPostForm qnAPostFormostForm, Principal principal) {
		SpUser user = this.spUserService.findbyUsername(principal.getName());
		long userid = user.getId();
		model.addAttribute("userid", userid);

		return "qna_post_form";
	}

	@PostMapping("/create")
	public String create(@Valid QnAPostForm qnAPostForm, BindingResult bindingResult,Principal principal) {
		if (bindingResult.hasErrors()) {
			System.out.println("에러가 있어요");
			return "qna_post_form";
		}
		
		this.qnA_PostService.create(qnAPostForm.getSubject(), qnAPostForm.getContent(), principal.getName());
		System.out.println("저장합니다");
		return "redirect:/qna/post/list";
	}

	@GetMapping("/modify/{id}")
	public String modify(Model model, QnAPostForm qnAPostForm, @PathVariable("id") Integer id) {
		QnA_Post qnA_Post = this.qnA_PostService.getOnePost(id);
		qnAPostForm.setSubject(qnA_Post.getSubject());
		qnAPostForm.setContent(qnA_Post.getContent());
		model.addAttribute("method", "put");
		return "qna_post_form";
	}

	@PutMapping("/modify/{id}")
	public String modify(@Valid QnAPostForm qnAPostForm, BindingResult bindingResult,
			@PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "qna_post_form";
		}
		QnA_Post qnA_Post = this.qnA_PostService.getOnePost(id);
		qnA_Post.setSubject(qnAPostForm.getSubject());
		qnA_Post.setContent(qnAPostForm.getContent());
		this.qnA_PostService.modify(qnA_Post);
		return "redirect:/qna/post/detail/" + id;
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		QnA_Post qnA_Post = this.qnA_PostService.getOnePost(id);
		this.qnA_PostService.delete(qnA_Post);
		return "redirect:/qna/post/list";
	}

}
