package com.example.mini.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.mini.config.SpUserGrade;
import com.example.mini.entity.SpUser;
import com.example.mini.repository.SpUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecurityService implements UserDetailsService {
	
	@Autowired
	private SpUserRepository spUserRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("사용자 인증 체크 " + username);
		
		Optional<SpUser> _user = this.spUserRepository.findByUsername(username);			
		log.info("사용자 인증 체크 " + _user.isPresent());
		if(_user.isEmpty()) {
			throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
		}
		log.info("사용자 인증 체크-1");
		SpUser spUser = _user.get();
		log.info("사용자 인증 체크 " + spUser);
		log.info("사용자 인증 체크0");
		List<GrantedAuthority> authorities = new ArrayList<>();
		log.info("사용자 인증 체크1");
		if ("admin".equals(username)) {
			log.info("사용자 인증 체크2");
		    authorities.add(new SimpleGrantedAuthority(SpUserGrade.ADMIN.getValue()));
		} else if("seller".equals(username)) {
		    authorities.add(new SimpleGrantedAuthority(SpUserGrade.SELLER.getValue()));
			log.info("사용자 인증 체크3");
		} 
		else {
		    authorities.add(new SimpleGrantedAuthority(SpUserGrade.BRONZE.getValue()));
			log.info("사용자 인증 체크4");
		}		
		
		return new User(spUser.getUsername(),spUser.getPassword(), authorities);
	}
	
	

}
