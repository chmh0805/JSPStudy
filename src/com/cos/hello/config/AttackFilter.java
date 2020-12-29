package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class AttackFilter implements Filter {
	
	// 마지막 순서
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("공격방어 필터 실행");
		// joinProc 일 때
		HttpServletRequest req = (HttpServletRequest) request;
		ParamChange newRequest = new ParamChange(req);
		String method = req.getMethod();
		if (method.equals("POST")) {
			// username에 <, > 들어오는것을 방어
			// 만약에 꺽쇠가 들어오면 전부 &lt; &gt; 로 치환
			// 다시 필터타게 할 예정
		}
		chain.doFilter(newRequest, response);
	}
	
	class ParamChange extends HttpServletRequestWrapper {

		public ParamChange(HttpServletRequest request) {
			super(request);
		}
		
		@Override
		public String getParameter(String name) {
			String value;
			if (name.equals("username")) {
				value = super.getParameter(name);
				value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				return value;
			} else {
				return super.getParameter(name);
			}
		}
	}
}
