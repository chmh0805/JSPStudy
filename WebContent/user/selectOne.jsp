<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>
<h1>User Info</h1>
<h3>${result}</h3>
<h3>${sessionScope.sessionUser.username }님 환영합니다 !</h3>
</body>
</html>