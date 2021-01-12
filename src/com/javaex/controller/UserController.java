package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.utill.WebUtill;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserController");

		String action = request.getParameter("action");
		System.out.println("action=" + action);

		if ("joinForm".equals(action)) {
			System.out.println("회원가입폼");
			WebUtill.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {
			System.out.println("회원가입");

			// dao --> insert() 저장

			// 파라미터 값 꺼내기
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");

			// vo로 묶기 --> vo만들기 생성자 추가
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo.toString());

			// dao클래스 insert(vo) 사용 --> 저장 --> 회원가입
			UserDao userdao = new UserDao();
			userdao.insert(userVo);

			// 포워드 --> joinOk.jsp
			WebUtill.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginForm".equals(action)) {
			System.out.println("로그인 폼");

			// 포워드 --> loginForm.jsp
			WebUtill.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {
			System.out.println("로그인");

			// 파라미터 id pw
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");

			// dao --> getUser();
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, pw);
			System.out.println(authVo); // id pw ---> no, name

			if (authVo == null) {// 로그인 실패
				System.out.println("로그인 실패");
				// 리다이렉트 --> 로그인폼
				WebUtill.redirect(request, response, "/mysite2/user?action=loginForm");

			} else { // 성공일 때
				System.out.println("로그인 성공");

				// 세션영역에 필요한값(vo) 넣어준다.
				// 세션영역에 저장 (세션메모리)
				// 성공일 때
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);

				WebUtill.redirect(request, response, "/mysite2/main");
			}

		}else if ("logout".equals(action)) {
			System.out.println("로그아웃");
			
			//세션영역에 있는 vo를 삭제한다.
			HttpSession session = request.getSession();
			//삭제 어트리뷰트
			session.removeAttribute("authUser");
			//초기화코드
			session.invalidate();
			
			WebUtill.redirect(request, response, "/mysite2/main");
			
		}else if ("modifyForm".equals(action)) {
			System.out.println("회원정보수정");
			
			//no값을 int로 저장
			int userNo = Integer.parseInt(request.getParameter("no"));
			
			//dao생성
			UserDao userDao = new UserDao();
			
			//dao에 값 넣고 vo에 저장
			UserVo userVo = userDao.getUser(userNo);	
			
			//어트리뷰트에 담기
			request.setAttribute("uVo", userVo);
			
			//값 포워드
			WebUtill.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		
		}else if ("modify".equals(action)) {
			System.out.println("정보수정");
			
			//파라비터 값
			int no = Integer.parseInt(request.getParameter("no"));
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			//파라미터 값 Vo에 넣기
			UserVo userVo = new UserVo(no, name, password, gender);
			
			UserDao userDao = new UserDao();
			userDao.userUpdate(userVo);
			
			//dao -->getUser
			UserVo authVo = userDao.getUser(no);
			
			//세션영역에 저장
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authVo);
			
			WebUtill.redirect(request, response, "/mysite2/main");
			
		}

	}
		
	
		
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
