package com.zuimeihui.tutorial.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class AuthenticationTokenFilter extends OncePerRequestFilter{
	private final static Log log = LogFactory.getLog(AuthenticationTokenFilter.class);
	
	@Autowired TokenService tokenService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String authToken = null;
		
		//下面的代码从 Header的Authorization中活的token，也可以从其他header, cookie等中获取，看客户端怎么传递token
		String requestHeader = request.getHeader("Authorization");//http标准header
		if(requestHeader != null && requestHeader.startsWith("Bearer ")) {
			authToken = requestHeader.substring(7);
		}
		if(log.isTraceEnabled()) {
			log.trace("token is " + authToken);
		}
		if(authToken != null) {
			UserDetails user = null;
			//查询token对应的用户
			user = tokenService.getUserFromToken(authToken);
			if(user != null) {
				//把user设置到SecurityContextHolder内，以spring使用
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
}
