package com.example.mini.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mini.dto.QnAPostForm;
import com.example.mini.entity.QnA_Post;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.QnA_PostRepository;

@Service
public class QnA_PostService {
	@Autowired
	private QnA_PostRepository qnA_PostRepository;

	public List<QnA_Post> getAllPost() {
		List<QnA_Post> list = this.qnA_PostRepository.findAll();
		return list;
	}

	public QnA_Post getOnePost(Integer id) {
		Optional<QnA_Post> oqpost = this.qnA_PostRepository.findById(id);
		if (oqpost.isPresent()) {
			return oqpost.get();
		}
		throw new DataNotFoundException("post not found");
	}

	public QnAPostForm getOnePostForm(Integer id) {
		Optional<QnA_Post> oqpost = this.qnA_PostRepository.findById(id);
		if (oqpost.isPresent()) {
			QnA_Post qnA_Post = oqpost.get();
			return QnAPostForm.builder()
					.subject(qnA_Post.getSubject())
					.content(qnA_Post.getContent())
					.build();
		}
		throw new DataNotFoundException("post not found");
	}

	public void create(String subject, String content, String username) {
		QnA_Post qnA_Post = new QnA_Post();
		qnA_Post.setSubject(subject);
		qnA_Post.setContent(content);
		qnA_Post.setUsername(username);
		qnA_Post.setCreateDate(LocalDateTime.now());
		this.qnA_PostRepository.save(qnA_Post);
	}

	public void modify(QnA_Post qnA_Post) {
		this.qnA_PostRepository.save(qnA_Post);

	}

	public void delete(QnA_Post qnA_Post) {
		this.qnA_PostRepository.delete(qnA_Post);
	}

}
