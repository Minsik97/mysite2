package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.utill.WebUtill;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/bc")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("BoardController");
			
			String action = request.getParameter("action");
			System.out.println("action=" + action);
			
			
			
			if("list".equals(action)) {
				System.out.println("게시판 리스트");
				
				BoardDao boardDao = new BoardDao();
				List<BoardVo> boardList = boardDao.getboardList();
				
				request.setAttribute("bList", boardList);
				
				WebUtill.forward(request, response, "/WEB-INF/views/board/list.jsp");
				
			}else if ("writeForm".equals(action)) {
				System.out.println("등록 폼");
				
				WebUtill.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
				
			}else if("write".equals(action)) {
				System.out.println("등록");
				
				HttpSession session = request.getSession();
				UserVo authUser = (UserVo)session.getAttribute("authUser");
				
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				int userNo = authUser.getNo();
				
				BoardVo boardVo = new BoardVo(title, content, userNo);
				
				BoardDao boardDao = new BoardDao();
				
				boardDao.boardWrite(boardVo);
				
				WebUtill.redirect(request, response, "/mysite2/bc?action=list");

			}else if ("read".equals(action)) {
				System.out.println("글 읽기");
				
				int no = Integer.parseInt(request.getParameter("no"));
				
				BoardDao boardDao = new BoardDao();
				
				BoardVo boardVo = boardDao.getbUser(no);
				
				request.setAttribute("boardVo", boardVo);
				
				WebUtill.forward(request, response, "/WEB-INF/views/board/read.jsp");
				
			}else if ("delete".equals(action)) {
				System.out.println("삭제");
				
				int no = Integer.parseInt(request.getParameter("no"));
				
				BoardDao boardDao = new BoardDao();
				
				boardDao.bDelete(no);
				
				WebUtill.redirect(request, response, "/mysite2/bc?action=list" );
		
			}else if ("modifyForm".equals(action)) {
				System.out.println("수정 폼");
				
				int no = Integer.parseInt(request.getParameter("no"));
				
				BoardDao boardDao = new BoardDao();
				
				BoardVo boardVo = boardDao.getbUser(no);
				
				request.setAttribute("boardVoM", boardVo);
				
				WebUtill.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
				
			}else if("modify".equals(action)) {
				System.out.println("수정");
				
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				
				HttpSession session = request.getSession();
				BoardVo authUser = (BoardVo) session.getAttribute("authUser");
				int no = authUser.getNo();
				
				BoardVo boardVo = new BoardVo(title, content);
				
				BoardDao boardDao = new BoardDao();
				boardDao.bModify(boardVo);
				
				authUser.setTitle(title);
				
				WebUtill.redirect(request, response, "/mysite2/bc?action=list");
				
			}
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
