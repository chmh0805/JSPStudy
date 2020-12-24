package com.cos.hello.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;

public class UsersJoinService {
	
	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 데이터 원형 : username=아이디&password=비밀번호&email=이메일
		// 1번 form의 input태그에 있는 3가지 값(username, password, email) 받기
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		// getParameter함수는 get방식의 데이터와 post방식의 데이터를 다 받을 수 있음 !!
        // 단, post 방식에서는 데이터 타입이 x-www-form-urlencoded 방식만 받을 수 있음.
		Users user = Users.builder()
						.username(username)
						.password(password)
						.email(email)
						.build();
		
		// 2번 DB에 연결해서 3가지 값을 INSERT 하기
		UsersDao usersDao = new UsersDao(); // 싱글톤 방식으로 바꾸기
		int result = usersDao.insert(user);
		
		// 3번 INSERT가 정상적으로 되었다면 login.jsp를 응답!!
		if (result == 1) {
			resp.sendRedirect("auth/login.jsp");
		} else {
			resp.sendRedirect("auth/join.jsp");
		}
	}
}
