package com.example.mini.dto;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QnAPostForm {

	@Transient
	private Long id;

	@NotEmpty(message = "제목이 없습니다.")
	private String subject;
	@NotEmpty(message = "본문이 없습니다.")
	private String content;
	private Long userid;

	@Builder
	public QnAPostForm(String subject, String content, Long userid) {
		super();
		this.subject = subject;
		this.content = content;
		this.userid = userid;
	}

}
