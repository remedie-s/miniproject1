package com.example.mini.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.mini.service.TokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	private final static String ACCESS_TOKEN_PREFIX = "Bearer";
	private final static String HEADER_AUTH = "Authentication";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String url = request.getRequestURI();
		if (url.startsWith("/sporder") || url.startsWith("/spuser") || url.startsWith("/homepage")
				|| url.startsWith("/product") || url.startsWith("/qna") || url.startsWith("/dist")
				|| url.startsWith("/plugins") || url.startsWith("/favicon.ico")) {
			filterChain.doFilter(request, response);
			return;
		}

		Cookie[] list = request.getCookies();
		String accessToken = "";
		String refreshToken = "";
		for (Cookie cookie : list) {
			if (cookie.getName().equals("access_token")) {
				accessToken = cookie.getValue();
			} else if (cookie.getName().equals("refreshToken")) {
				refreshToken = cookie.getValue();
			}
		}

		if (tokenProvider.isVaildToken(accessToken)) {
			System.out.println("유효한 토큰");
			filterChain.doFilter(request, response);
		} else {
			System.out.println("유효하지 않은 토큰");
			if (!tokenProvider.isVaildToken(refreshToken)) {
				System.out.println("리프레시토큰 문제 발생");
				response.sendRedirect("/spuser/signup");
				return;
			}

			if (!tokenProvider.isVaildToken(refreshToken)) {
				System.out.println("리프레시 토큰이 디비에 등록 X");
				response.sendRedirect("/spuser/signup");
				return;
			}

			response.sendRedirect("/spuser/reissue/" + refreshToken);
		}

	}
}
