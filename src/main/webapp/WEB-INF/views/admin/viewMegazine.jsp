<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>집밥의민족</title>
		<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
		<link rel="shortcut icon" type="image/png" href="../assets/images/logos/favicon.png" />
		<link rel="stylesheet" href="../assets/css/styles.min.css" />
		<link rel="stylesheet" href="/css/admin/view-megazine.css" />
		<script src="/js/admin/view-megazine.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
		<script src="/js/common/jwt.js"></script>
		<script src="/js/common/util.js"></script>
	</head>
	
	<body>
		<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
			data-sidebar-position="fixed" data-header-position="fixed">
			
			<!-- 사이드바 -->
			<%@include file="./common/sidebar.jsp" %>
			
			<div class="body-wrapper">
				<!-- 헤더 -->
				<%@include file="./common/header.jsp" %>
				
				<div class="container-fluid">
					<!-- 네비게이션 바 -->
					<div class="nav_wrap">
						<a href="/admin/home.do"><span>메인</span></a>
						<a href=""><img src="/images/cooking/arrow_right.png"><span>게시판</span></a>
						<a href="/admin/listChomp.do"><img src="/images/cooking/arrow_right.png"><span>쩝쩝박사</span></a>
						<a href="/admin/listMegazine.do"><img src="/images/cooking/arrow_right.png"><span>매거진 관리</span></a>
						<a class="active" href="/admin/writeMegazine.do"><img src="/images/cooking/arrow_right.png"><span>매거진 추가</span></a>
					</div>
					
					<!-- 제목 -->
					<h1>쩝쩝박사 게시판</h1>
					<div class="bar">
						<div class="tab">
							<ul>
								<li class="btn_tab"><a href="/admin/listChomp.do"><span>전체</span></a></li>
								<li class="btn_tab"><a href="/admin/listVote.do"><span>투표 관리</span></a></li>
								<li class="btn_tab"><a href="/admin/listMegazine.do" class="active"><span>매거진 관리</span></a></li>
								<li class="btn_tab"><a href="/admin/listEvent.do"><span>이벤트 관리</span></a></li>
							</ul>
						</div>
					</div>
					
						
					<!-- 본문 -->
					<div class="card overflow-hidden">
						<div class="card-body p-4">
							<!-- 제목 -->
							<h2 class="title fs-9 fw-semibold my-4 mt-2"></h2>
							<div class="d-flex align-items-center gap-2">
								<div class="d-flex align-items-center gap-2"><b>집밥의민족</b></div>
								<div class="d-flex align-items-center gap-2">
									<i class="ti ti-point text-dark fs-5"></i>
									<!-- 작성일 -->
									<span class="postdate"></span>
								</div>
							</div>
						</div>
						<!-- 내용 -->
						<div class="content card-body border-top p-4 mb-3"></div>
						<!-- 버튼 -->
            			<div class="p-3">
            				<div class="text-end">
            					<button id="editMegazineBtn" type="button" class="btn btn-info px-4">수정하기</button>
            					<button id="deleteMegazineBtn" type="button" class="btn btn-danger ms-6 px-4">삭제하기</button>
            					<button type="button" class="btn btn-light ms-6 px-4" onclick="location.href='/admin/listMegazine.do';">목록으로</button>
            				</div>
            			</div>
					</div>
				</div>
			</div>
		</div>

		<script src="../assets/libs/jquery/dist/jquery.min.js"></script>
		<script src="../assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
		<script src="../assets/js/sidebarmenu.js"></script>
		<script src="../assets/js/app.min.js"></script>
		<script src="../assets/libs/simplebar/dist/simplebar.js"></script>
		<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
	</body>

</html>