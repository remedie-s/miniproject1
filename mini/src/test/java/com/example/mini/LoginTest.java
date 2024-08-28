package com.example.mini;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.mini.entity.SpUser;
import com.example.mini.repository.SpUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class LoginTest {
	@Autowired
	private SpUserRepository spUserRepository;
	
	@Test
	void loginTest() {
		Optional<SpUser> _user = spUserRepository.findByUsername("aaaaa");
		if(_user.isEmpty()) {
			throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
		}
		log.info("사용자 인증 체크-1");
		SpUser spUser = _user.get();
		log.info("사용자 인증 체크 " + spUser);
	}
}
