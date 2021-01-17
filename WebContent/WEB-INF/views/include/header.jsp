<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="header">
			<h1><a href="/mysite2/main">MySite</a></h1>
			
			<!-- if 로그인이 안됐을 때(session영역에 값이 없으면) -->
			<c:choose>
				<c:when test="${empty sessionScope.authUser }">
					<ul>
						<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
						<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul><!-- if 로그인 성공했을 때 (session영역에 값이 있으면) -->
						<li>${sessionScope.authUser.name }님 안녕하세요^^</li>
						<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
						<li><a href="/mysite2/user?action=modifyForm">회원정보수정</a></li>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
		<!-- //header -->

	
		<div id="nav">
			<ul>
				<li><a href="/mysite2/gbc?action=addList">방명록</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="/mysite2/bc?action=list">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<!-- //nav -->