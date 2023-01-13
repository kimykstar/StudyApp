<%@ page import="com.db.ConnectDB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	ConnectDB connectDB = ConnectDB.getInstance();
	
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String name = request.getParameter("name");
	String phone = request.getParameter("phone");
	String returns = connectDB.joinDB(id, pw, name, phone);
	
	out.println(returns);
	
%>
