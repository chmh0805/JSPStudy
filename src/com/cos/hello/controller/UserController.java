package com.cos.hello.controller;

import java.io.IOException;

// javax로 시작하는 패키지는 tomcat이 들고있는 라이브러리
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.hello.service.UsersService;

// 디스패쳐의 역할 = 분기 = 필요한 View를 응답해주는 것
public class UserController extends HttpServlet {
	// req와 resp는 톰캣이 만들어준다. (클라이언트의 요청이 있을 때마다)
	// req는 BufferedReader할 수 있는 ByteStream
	// resp는 BufferedWriter할 수 있는 ByteStream
	
	// http://localhost:8000/hello/user
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("FrontController 실행됨");
		
		String gubun = req.getParameter("gubun"); // hello/front
		System.out.println(gubun);
		
		route(gubun, req, resp);
	}
		
	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UsersService usersService = new UsersService();
		
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // 한번 더 request
			
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // 한번 더 request
			
		} else if (gubun.equals("selectOne")) { // 유저정보보기
			usersService.유저정보보기(req, resp);
			
		} else if (gubun.equals("updateOne")) {
			usersService.유저정보수정페이지(req, resp);
			
		} else if (gubun.equals("updateProc")) {
			usersService.유저정보수정(req, resp);
			
		} else if (gubun.equals("joinProc")) { // 회원가입 수행 (joinProcess의 약자)
			usersService.회원가입(req, resp);
			
		} else if (gubun.equals("loginProc")) {
			usersService.로그인(req, resp);
			
		} else if (gubun.equals("deleteProc")) {
			usersService.회원탈퇴(req, resp);
		}
	}
}