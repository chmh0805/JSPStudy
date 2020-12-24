<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.cos.hello.config.OracleConn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	PreparedStatement pstmt = null;

	Connection conn = OracleConn.getInstance();
	pstmt = conn.prepareStatement("SELECT * FROM emp");
	ResultSet rs = pstmt.executeQuery();
	
	while (rs.next()) {
		out.println("empno : " + rs.getString("empno"));
	}
%>