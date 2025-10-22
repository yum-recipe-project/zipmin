<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>집밥의민족</title>
		<link rel="shortcut icon" type="image/png" href="../assets/images/logos/favicon.png" />
		<link rel="stylesheet" href="../assets/css/styles.min.css" />
		<link rel="stylesheet" href="/css/admin/list-fridge.css" />
		<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
		<script src="/js/common/pagination.js"></script>
		<script src="/js/common/jwt.js"></script>
		<script src="/js/common/util.js"></script>
		<script src="/js/admin/list-fridge.js"></script>
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
						<a href="/admin/listGuide.do" class="active"><img src="/images/cooking/arrow_right.png"><span>키친가이드</span></a>
					</div>
					
					<!-- 제목 -->
					<h1>키친가이드 게시판</h1>
					<div class="bar">
						<div class="tab">
							<ul>
								<li class="btn_tab"><a href="" data-tab="" class="active"><span>전체</span></a></li>
								<li class="btn_tab"><a href="" data-tab="preparation"><span>손질법</span></a></li>
								<li class="btn_tab"><a href="" data-tab="storage"><span>보관법</span></a></li>
								<li class="btn_tab"><a href="" data-tab="cooking"><span>요리정보</span></a></li>
								<li class="btn_tab"><a href="" data-tab="etc"><span>기타정보</span></a></li>
							</ul>
						</div>
					</div>
					
					
				</div>
			</div>
		</div>
	</body>
</html>