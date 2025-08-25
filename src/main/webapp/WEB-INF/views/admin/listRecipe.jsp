<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>집밥의민족</title>
		<link rel="shortcut icon" type="image/png" href="../assets/images/logos/favicon.png" />
		<link rel="stylesheet" href="../assets/css/styles.min.css" />
		<link rel="stylesheet" href="/css/admin/list-recipe.css" />
		<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
		<script src="/js/common/jwt.js"></script>
		<script src="/js/common/util.js"></script>
		<script src="/js/common/pagination.js"></script>
		<script src="/js/admin/list-recipe.js"></script>
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
						<a href=""><img src="/images/cooking/arrow_right.png"><span>댓글/리뷰</span></a>
						<a href="/admin/listReview.do" class="active"><img src="/images/cooking/arrow_right.png"><span>리뷰</span></a>
					</div>
					
					<!-- 제목 -->
					<h1>레시피 게시판</h1>
					<div class="bar">
						<div class="tab">
							<ul>
								<li class="btn_tab"><a href="" data-tab="" class="active"><span>전체</span></a></li>
								<li class="btn_tab"><a href="" data-tab="recipe"><span>레시피</span></a></li>
							</ul>
						</div>
					</div>
		
					<!-- 목록 -->
					<table class="table text-nowrap mb-0 align-middle fixed-table">
					    <thead class="text-dark fs-4">
					    	<colgroup>
					    		<col style="width:8%">
					    		<col style="width:20%">
					    		<col style="width:10%">
					    		<col style="width:15%">
					    		<col style="width:9%">
					    		<col style="width:11%">
					    		<col style="width:10%">
					    		<col style="width:10%">
					    		<col style="width:7%">
					    	</colgroup>
					        <tr>
					            <th colspan="2" class="total text-start"></th>
					            <th></th>
					            <th></th>
					            <th></th>
					            <th></th>
					            <th colspan="3" class="text-end">
					            	<form class="search position-relative text-end">
					            		<input type="text" class="form-control search-chat py-2 ps-5" id="text-srh" placeholder="검색어를 입력하세요">
					            		<i class="ti ti-search position-absolute top-50 start-0 translate-middle-y fs-6 text-dark ms-3"></i>
					            	</form>
					            </th>
					        </tr>
					        <tr class="table_th">
					            <th class="sort_btn desc" data-key="id">
					                <h6 class="fs-4 fw-semibold mb-0">No</h6>
					            </th>
					            <th class="sort_btn" data-key="title">
					                <h6 class="fs-4 fw-semibold mb-0">제목</h6>
					            </th>
					            <th>
					                <h6 class="fs-4 fw-semibold mb-0">작성자</h6>
					            </th>
					            <th class="sort_btn" data-key="postdate">
					                <h6 class="fs-4 fw-semibold mb-0">작성일</h6>
					            </th>
					            <th class="sort_btn" data-key="reviewscore">
					                <h6 class="fs-4 fw-semibold mb-0">별점</h6>
					            </th>
					            <th class="sort_btn" data-key="likecount">
					                <h6 class="fs-4 fw-semibold mb-0">좋아요수</h6>
					            </th>
					            <th class="sort_btn" data-key="commentcount">
					                <h6 class="fs-4 fw-semibold mb-0">댓글수</h6>
					            </th>
					            <th class="sort_btn" data-key="reportcount">
					                <h6 class="fs-4 fw-semibold mb-0">신고수</h6>
					            </th>
					            <th></th>
					        </tr>
					    </thead>
					    <tbody class="recipe_list"></tbody>
					</table>

					<!-- 페이지네이션 -->
					<div class="pagination_wrap">
						<div class="pagination">
						    <ul></ul>
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
	</body>

</html>