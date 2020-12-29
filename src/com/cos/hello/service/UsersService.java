package com.cos.hello.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.dto.JoinDto;
import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;
import com.cos.hello.util.Script;

public class UsersService {
	
	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JoinDto loginDto = (JoinDto)req.getAttribute("dto");

		UsersDao usersDao = new UsersDao(); // 싱글톤 방식으로 바꾸기
		int result = usersDao.insert(loginDto);
		
		// 3번 INSERT가 정상적으로 되었다면 login.jsp를 응답!!
		if (result == 1) {
			Script.href(resp, "auth/login.jsp", "회원가입 완료. 로그인해주세요.");
		} else {
			Script.back(resp, "회원가입 실패. 입력값을 확인해주세요.");
		}
	}
	
	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Users userEntity = new Users();
		// SELECT id, username, email FROM users WHERE username = ? AND password = ?;
		// DAO의 함수명 : login() : Users 오브젝트를 return하면 된다.
		// 정상 : 세션에 Users 오브젝트 담고 index.jsp
		// 비정상 : login.jsp
		
		// 1번 전달된 값 받기
		LoginDto loginDto = (LoginDto)req.getAttribute("dto");
		// 2번 DB에 값이 있는지 SELECT해서 확인
		UsersDao usersDao = new UsersDao();
		userEntity = usersDao.login(loginDto);
		// 3번 DB에 값이 있으면 세션에 값을 추가하고 이동
		if (userEntity != null) {
			HttpSession httpSession = req.getSession(); // 세션 영역(Heap)에 접근
			httpSession.setAttribute("sessionUser", userEntity); // 세션 영역에 키값 저장
			// 4번 SELECT가 정상적으로 되었다면 index.jsp로 이동
			// 한글 처리를 위해 resp 객체를 건드린다.
			// MIME 타입
			// Http Header에 Content-Type
			Script.href(resp, "index.jsp", "로그인 성공");
		} else {
			HttpSession httpSession = req.getSession(); // 세션 영역(Heap)에 접근
			httpSession.setAttribute("sessionUser", userEntity); // 세션 영역에 키값 저장
			// 4번 SELECT가 정상적으로 되었다면 index.jsp로 이동
			Script.back(resp, "로그인 실패");
		}
	}
	
	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 인증이 필요한 페이지
		HttpSession session = req.getSession();
		
		Users user = (Users)session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		} else {
			Script.back(resp, "로그인이 필요합니다.");
		}
	}
	
	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 인증이 필요한 페이지
		HttpSession session = req.getSession();
		
		Users user = (Users)session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
		} else {
			Script.back(resp, "로그인이 필요합니다.");
		}
	}
	
	public void 유저정보수정(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		
		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("sessionUser");
		
		Users userEntity = Users.builder()
				.id(id)
				.password(password)
				.email(email)
				.build();
		
		UsersDao usersDao = new UsersDao();
		int result = usersDao.updateById(userEntity);
		
		if (result == 1) {
			Script.href(resp, "index.jsp", "수정 완료");
		} else {
			// 이전 페이지로 이동
			Script.back(resp, "수정 실패");
		}
	}
	
	public void 회원탈퇴(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		
		UsersDao usersDao = new UsersDao();
		int result = usersDao.delete(id);
		
		if (result == 1) {
			HttpSession session = req.getSession();
			session.invalidate(); // JSession ID영역을 통으로 날림.
			Script.href(resp, "index.jsp", "회원탈퇴 완료");
		} else {
			// 이전 페이지로 이동
			Script.back(resp, "실패!!!!!!!!!!!");
		}
	}
}
