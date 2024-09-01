package com.example.mini.controller;

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
import com.example.mini.service.QnA_PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna/post")
public class QnA_PostController {
	private final QnA_PostService qnA_PostService;

	@GetMapping("/list")
	public String list(Model model) {
		List<QnA_Post> posts = this.qnA_PostService.getAllPost();

		model.addAttribute("posts", posts);
		return "qna_post_list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id,
			QnAReveiwForm reviewForm) {
		QnA_Post post = this.qnA_PostService.getOnePost(id);
		model.addAttribute("post", post);
		return "qna_post_detail";
	}

	@GetMapping("/create")
	public String create(Model model, QnAPostForm qnAPostFormostForm) {
		model.addAttribute("method", "post");
		return "qna_post_form";
	}

	@PostMapping("/create")
	public String create(@Valid QnAPostForm qnAPostForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "qna_post_form";
		}
		this.qnA_PostService.create(qnAPostForm.getSubject(), qnAPostForm.getContent());
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
