package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		// req.getParameter 함수 실행 시에 파싱하기 때문에
		// 파싱 전에 인코딩 해줘야 함.
		response.setContentType("text/html; charset=utf-8");
		// resp.setHeader("content-type", "text/html; charset=UTF-8"); 과 동일!!
		chain.doFilter(request, response);
	}
	
}
