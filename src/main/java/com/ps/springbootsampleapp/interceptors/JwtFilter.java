package com.ps.springbootsampleapp.interceptors;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ps.springbootsampleapp.controller.TokenProvider;
import com.ps.springbootsampleapp.security.MyUserDetailsService;
import com.ps.springbootsampleapp.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil util;

	@Autowired
	MyUserDetailsService uds;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");

		String jwt = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer")) {
			jwt = authHeader.substring(7);
			username = util.extractUsername(jwt);

		}

		if (authHeader != null && authHeader.startsWith("Bearer") && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.uds.loadUserByUsername(username);
			if (util.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}

		}

		filterChain.doFilter(request, response);

	}

}
