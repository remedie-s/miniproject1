package com.example.mini.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpUserForm {
	@Size(min = 3, max = 32)
	@NotEmpty(message = "아이디를 입력하세요")
	private String username;
	@NotEmpty(message = "비밀번호를 입력하세요")
	private String password1;
	@NotEmpty(message = "비밀번호 확인을 입력하세요")
	private String password2;
	@NotEmpty(message = "이름을 입력하세요")
	private String first_name;
	@NotEmpty(message = "성을 입력하세요")
	private String last_name;
	@NotEmpty(message = "핸드폰번호를 입력하세요")
	private String phone_number;
	@NotEmpty(message = "이메일을 입력하세요")
	@Email
	private String e_mail;

}
