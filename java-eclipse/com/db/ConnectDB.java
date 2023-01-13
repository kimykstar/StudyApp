package com.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {
	private static ConnectDB instance = new ConnectDB();
	
	public static ConnectDB getInstance() {
		return instance;
	}
	
	// oracle 계정
		String jdbcURL = "jdbc:oracle:thin:@localhost:1521/xe";
		String userID = "chat";
		String userPW = "1234";
		
		String sql = "";
		String sql2 = "";
		String returns = "jdbc loading complete";
		String check;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
	
	public ConnectDB() {}
	
	
	public String loginDB(String id, String pw) {
		sql = "SELECT * FROM user_info WHERE id = ? and pw = ?";
		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(jdbcURL, userID, userPW);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(2).equals(pw))
					check = "true";
			}else {
				check = "false";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(pstmt2 != null)try {pstmt2.close();} catch(SQLException ex) {}
			if(pstmt != null)try {pstmt.close();} catch(SQLException ex) {}
			if(conn != null)try {conn.close();} catch(SQLException ex) {}
		}
		
		return check;
	}
	
	// id와 pw, name, phone을 db에 삽입한다.
	public String joinDB(String id, String pwd, String name, String phone) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(jdbcURL, userID, userPW);
			sql = "SELECT id FROM user_info WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			// 1번 ?에 id변수 값을 넣는다.
			pstmt.setString(1, id);
			
			// sql문 실행(SELECT문은 executeQuery문)
			// 회원가입 시 id의 중복체크 여부 확인
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = "이미 존재하는 아이디 입니다.";
			}else {
				sql2 = "INSERT INTO user_info VALUES (?, ?, ?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.setString(3, name);
				pstmt2.setString(4, phone);
				// sql2문 실행 (INSERT, DELETE, UPDATE문은 executeUpdate문)
				pstmt2.executeUpdate();
				returns = "회원가입 성공!";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(pstmt2 != null)try {pstmt2.close();} catch(SQLException ex) {}
			if(pstmt != null)try {pstmt.close();} catch(SQLException ex) {}
			if(conn != null)try {conn.close();} catch(SQLException ex) {}
		}
		return returns;
	}
}
