<%@ page import="com.db.ConnectDB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	ConnectDB connectDB = ConnectDB.getInstance();
	
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String returns = connectDB.loginDB(id, pw);
	
	out.println(returns);
	
%>
</body>
</html>