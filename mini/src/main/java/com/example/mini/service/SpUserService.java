package com.example.mini.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mini.config.SpUserGrade;
import com.example.mini.entity.SpUser;
import com.example.mini.exception.DataNotFoundException;
import com.example.mini.repository.SpUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SpUserService {
	private final SpUserRepository spUserRepository;
	private final PasswordEncoder passwordEncoder;
	
	

	public SpUser create(String username, String password, String first_name, String last_name,
			String phone_number, String e_mail) {
		SpUser user = new SpUser();
		user.setUsername(username);
		user.setPassword(this.passwordEncoder.encode(password));
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		user.setPhone_number(phone_number);
		user.setE_mail(e_mail);
		user.setSpuser_grade(SpUserGrade.BRONZE);
		user.setAddressList(null);//TODO AddressList 고쳐야함
		user.setCreate_date(LocalDateTime.now());
		try {
			this.spUserRepository.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return user;
	}

	public SpUser findbyId(Long userId) {
		Optional<SpUser> spuser = this.spUserRepository.findById(userId);
		if(spuser.isPresent()) {
			return spuser.get();
		}
		throw new DataNotFoundException("user not found");
	}

	public SpUser findbyUsername(String name) {
		Optional<SpUser> user = this.spUserRepository.findByUsername(name);
		if(user.isPresent()) {
			return user.get();
		}
		throw new DataNotFoundException("user not found");
	}

	
}
