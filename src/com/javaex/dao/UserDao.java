package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// DB접속
	private void getConnection() {
		// DB접속 기능
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			System.out.println("[접속성공]");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// 자원정리
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	//등록
	public int insert(UserVo userVo) {

		int count = 0;

		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			String query = "";
			query += " insert into users ";
			query += " values(seq_uno.nextval,  ";
			query += "				 ?, ";
			query += "               ?, ";
			query += "               ?, ";
			query += "               ?  ";
			query += " ) ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			count = pstmt.executeUpdate(); // 쿼리문실행

			// 4.결과처리
			System.out.println("insert " + count + "건 회원정보 저장");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}
	
	public UserVo getUser(String id, String pw) {
		UserVo userVo = null;
		
		getConnection();

		try {
			String query = "";
			query += " select  no, ";
			query += "             name ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery(); //쿼리문 실행
			
			//결과처리
			while(rs.next()){
					int no = rs.getInt("no");
					String name = rs.getString("name");
					
					userVo = new UserVo(no, name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return userVo;

	}
	//수정
	public int userUpdate(UserVo userVo) {
		
		int count = 0;
		
		//DB접속
		getConnection();
		
		try {
			String query = "";
					   query +=  " update users ";
					   query +=  " set name = ?, ";
					   query +=  " 		 password = ?, ";
					   query +=  "		 gender = ? ";
					   query +=  " where no = ? ";
			
		    pstmt = conn.prepareStatement(query);
		    pstmt.setString(1, userVo.getName());
		    pstmt.setString(2, userVo.getPassword());
		    pstmt.setString(3, userVo.getGender());
		    
		    count = pstmt.executeUpdate();
					
		    //결과처리
		    System.out.println("[dao]" + count + "건 수정");
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return count;
		
	}
	
	public UserVo getUser(int userNo) {
		UserVo userVo = null;
		
		getConnection();
		
		try {
			String query = "";
						query += " select no, ";
						query += " 			  id, ";
						query += " 	      	  password, ";
						query += "  		  name,";
						query += "  		  gender ";
						query +=" from users ";
						query += " where no = ? ";
						
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userNo);
			
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String gender = rs.getString("gender");
				
				userVo = new UserVo(no, id, password, name,  gender);
			}
		
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return userVo;
		
	}

}
