<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>집밥의민족</title>
		<link rel="shortcut icon" type="image/png" href="../assets/images/logos/favicon.png" />
		<link rel="stylesheet" href="../assets/css/styles.min.css" />
		<link rel="stylesheet" href="/css/admin/list-comment.css" />
		<script src="/js/common/util.js"></script>
		<script src="/js/admin/list-comment.js"></script>
	</head>
	
	<body>
		<!--  Body Wrapper -->
		<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
			data-sidebar-position="fixed" data-header-position="fixed">
			
			<!-- Sidebar -->
			<%@include file="./common/sidebar.jsp" %>
			
			<!--  Main wrapper -->
			<div class="body-wrapper">
				<%@include file="./common/header.jsp" %>
				
				<main id="container">
					<div class="content">
					
						<!-- 네비게이션 바 -->
						<div class="nav_wrap">
							<a href="/admin/home.do"><span>메인</span></a>
							<a href="">
								<img src="/images/cooking/arrow_right.png">
								<span>댓글/리뷰</span>
							</a>
							<a class="active" href="">
								<img src="/images/cooking/arrow_right.png">
								<span>댓글</span>
							</a>
						</div>
						
						<h1>댓글 관리</h1>
						<div class="bar">
							<div class="tab">
								<ul>
									<li class="btn_tab" data-testid="tabItem">
										<a class="active"><span>전체</span></a>
									</li>
									<li class="btn_tab" data-testid="tabItem">
										<a><span>투표</span></a>
									</li>
									<li class="btn_tab" data-testid="tabItem">
										<a><span>매거진</span></a>
									</li>
									<li class="btn_tab" data-testid="tabItem">
										<a><span>이벤트</span></a>
									</li>
									<li class="btn_tab" data-testid="tabItem">
										<a><span>매거진</span></a>
									</li>
									<li class="btn_tab" data-testid="tabItem">
										<a><span>매거진</span></a>
									</li>
								</ul>
							</div>
							<button type="button" class="btn btn-info m-1">
								<i class="ti ti-plus fs-4"></i>
								투표 추가
							</button>
						</div>
			
						<table class="table text-nowrap mb-0 align-middle">
						    <thead class="text-dark fs-4">
						        <tr>
						            <th class="total"></th>
						            <th></th>
						            <th></th>
						            <th></th>
						            <th></th>
						            <th></th>
						            <th><i class="ti ti-search fs-6"></i></th>
						        </tr>
						        <tr class="table_th">
						            <th>
						                <h6 class="fs-4 fw-semibold mb-0">No</h6>
						            </th>
						            <th>
						                <h6 class="fs-4 fw-semibold mb-0">제목</h6>
						            </th>
						            <th>
						                <h6 class="fs-4 fw-semibold mb-0">참여 기간</h6>
						            </th>
						            <th>
						                <h6 class="fs-4 fw-semibold mb-0">상태</h6>
						            </th>
						            <th>
						                <h6 class="fs-4 fw-semibold mb-0">참여자수</h6>
						            </th>
						            <th>
						                <h6 class="fs-4 fw-semibold mb-0">댓글수</h6>
						            </th>
						            <th></th>
						        </tr>
						    </thead>
						    <tbody class="vote_list"></tbody>
						</table>
	
						<!-- 페이지네이션 -->
						<div class="pagination_wrap">
							<div class="pagination">
							    <ul></ul>
							</div>
						</div>
					
					</div>
				</main>
			</div>
		</div>
		
		<script src="../assets/libs/jquery/dist/jquery.min.js"></script>
		<script src="../assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
		<script src="../assets/js/sidebarmenu.js"></script>
		<script src="../assets/js/app.min.js"></script>
		<script src="../assets/libs/simplebar/dist/simplebar.js"></script>
	</body>

</html>