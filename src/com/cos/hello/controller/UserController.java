package com.cos.hello.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

// javax로 시작하는 패키지는 tomcat이 들고있는 라이브러리
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.config.DBConn;
import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;
import com.cos.hello.service.UsersJoinService;

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
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // 한번 더 request
			
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // 한번 더 request
			
		} else if (gubun.equals("selectOne")) { // 유저정보보기
			// 인증이 필요한 페이지
			String result;
			HttpSession session = req.getSession();
			if (session.getAttribute("sessionUser") != null) {
				Users user = (Users)session.getAttribute("sessionUser");
				result = "인증되었습니다.";
				System.out.println(user);
			} else {
				result = "인증되지 않았습니다.";
			}
			req.setAttribute("result", result);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
			
		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp");
			
		} else if (gubun.equals("joinProc")) { // 회원가입 수행 (joinProcess의 약자)
			UsersJoinService usersJoinService = new UsersJoinService();
			usersJoinService.회원가입(req, resp);
			
		} else if (gubun.equals("loginProc")) {
			// SELECT id, username, email FROM users WHERE username = ? AND password = ?;
			
			// 1번 전달된 값 받기
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			System.out.println("=======loginProc Start=======");
			System.out.println(username);
			System.out.println(password);
			System.out.println("=======loginProc End=======");
			
			// 2번 DB에 값이 있는지 SELECT해서 확인
			// 생략
			Users user = Users.builder()
					.id(1)
					.username(username)
					.build();
			// 3번
			HttpSession session = req.getSession(); // 세션 영역(Heap)에 접근
			// session에는 사용자 패스워드는 절대 넣으면 안됨!!(보안상 문제)
			session.setAttribute("sessionUser", user); // 세션 영역에 키값 저장
			// 모든 응답에는 jSessionId가 쿠키로 추가된다.
			
			// 4번 SELECT가 정상적으로 되었다면 index.jsp로 이동
			resp.sendRedirect("index.jsp");
		}
	}
}