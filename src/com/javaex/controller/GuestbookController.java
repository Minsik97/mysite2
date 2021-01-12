package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.gdao.GuestbookDao;
import com.javaex.gutil.gWebUtil;
import com.javaex.gvo.GuestbookVo;
import com.javaex.utill.WebUtill;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println("action=" + action);

		if ("addList".equals(action)) {
			System.out.println("방명록리스트");

			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestbookVo> guestbookList = guestbookDao.getgdList();

			// 데이터 전달
			request.setAttribute("gbList", guestbookList);

			// 포워드 -->addList.jsp
			WebUtill.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if ("add".equals(action)) {
			System.out.println("등록");

			// 파라미터 값 4개
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			String reg_date = request.getParameter("reg_date");

			// vo로 묶기
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content, reg_date);

			// dao 생성
			GuestbookDao guestbookDao = new GuestbookDao();

			// dao에 vo넣기
			guestbookDao.gbInsert(guestbookVo);

			gWebUtil.redirect(request, response, "/mysite2/gbc?action=addList");

		} else if ("deleteForm".equals(action)) {
			System.out.println("삭제폼");

			gWebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");

		} else if ("delete".equals(action)) {
			System.out.println("삭제");

			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			GuestbookDao guestbookDao = new GuestbookDao();

			// 데이터 저장
			int ex = guestbookDao.gbDelete(no, password);

			if (ex == 1) {
				gWebUtil.redirect(request, response, "/mysite2/gbc?action=addList");

			} else if (ex == 0) {
				gWebUtil.forward(request, response, "/WEB-INF/views/guestbook/passwordcheck.jsp");
			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
