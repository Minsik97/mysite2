package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	// 생성자

	// 메소드 g/s

	// 메소드 일반

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
	
	
	//list
	public List<BoardVo> getboardList(){
		List<BoardVo>bList = new ArrayList<BoardVo>();
		
		getConnection();
		
		try {
			String query = "";
					    query += " select b.no, ";
					    query += "    		  b.title, ";
					    query += " 			  u.name, ";
					    query += "			  b.content, ";
					    query += " 			  b.hit, ";
					    query += "  		  b.reg_date, ";
					    query += "  		  b.user_no ";
					    query += " from board b, users u ";
					    query += " where b.user_no = u.no ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
					int no = rs.getInt("no");
					String title = rs.getString("title");
					String name = rs.getString("name");
					String content = rs.getString("content");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					int user_no = rs.getInt("user_no");
					BoardVo boardVo = new BoardVo(no, title, name, content, hit, reg_date, user_no);
					bList.add(boardVo);
				
			}
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return bList;
	
	}
	
	//write
	public int boardWrite(BoardVo boardVo) {
		
		int count = 0;
		
		//DB접속
		getConnection();
		
		
		try{
			String query = "";
						query += " insert into board ";
						query += " values( seq_bno.nextval, ";
						query += " 				?, ";
						query += "  			?, ";
						query += "  			default, ";
						query += "  			sysdate, ";
						query += "  			?	";
						query += " ) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUser_no());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("[dao]" + count + "건 저장");
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		//자원정리
		close();
		return count;
		
	}
	
	//read (회원정보 가져오기)
	public BoardVo getbUser (int No){
		BoardVo boardVo = null;
		
		//DB접속
		getConnection();
		
		try {
			String query = "";
					   query += " select b.no, ";
					   query += "			 u.name, ";
					   query += "  			 b.hit, ";
					   query += "   		 b.reg_date, ";
					   query += "   		 b.title, ";
					   query += "   		 b.content, ";
					   query += "   		 b.user_no ";
					   query += " from board b, users u ";
					   query += " where b.user_no = u.no ";
					   query += " and b.no = ? ";
					   
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, No);
			
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int user_no = rs.getInt("user_no");
				
				boardVo = new BoardVo(no,name, hit, reg_date, title, content, user_no);		
			}

		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return boardVo;
		
	}
	
	//삭제
	public int bDelete(int no) {
		
		int count = 0;
		
		// DB접속
		getConnection();
		
		try {
			String query = " delete from board where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
			//결과처리 
			System.out.println("[dao]" + count + "건 삭제");
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		// 자원정리
		close();

		return count;		
		
	}
	
	//수정
	public int bModify(BoardVo boardVo) {
		
		int count = 0;
		
		//DB접속
		getConnection();
		
		
		try {
			String query ="";
						query += " update  board ";
						query += " set title = ?, ";
						query += "  	 content = ? ";
						query += " where no = ? ";
					
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("[dao]" + count + "건 수정");
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return count;
		
	}
	

}
