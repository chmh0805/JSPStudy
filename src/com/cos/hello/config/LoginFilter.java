package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cos.hello.dto.LoginDto;

public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String gubun = req.getParameter("gubun");
		
		if (gubun.equals("loginProc")) {
			if (req.getParameter("username") == null ||
					req.getParameter("password") == null ||
						req.getParameter("username").equals("") ||
						req.getParameter("password").equals("")
				) {
				chain.doFilter(request, response);
				return;
			}
			
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			LoginDto dto = new LoginDto();
			dto.setUsername(username);
			dto.setPassword(password);
			
			request.setAttribute("dto", dto);
		}
		
		chain.doFilter(request, response);
	}
}
