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
		<link rel="stylesheet" href="/css/admin/list-guide.css" />
		<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
		<script src="/js/common/pagination.js"></script>
		<script src="/js/common/jwt.js"></script>
		<script src="/js/common/util.js"></script>
		<script src="/js/admin/list-withdraw.js"></script>
		<script src="/js/admin/modal/edit-guide-modal.js"></script>
		<script src="/js/admin/modal/write-guide-modal.js"></script>
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
						<a href=""><img src="/images/cooking/arrow_right.png"><span>회원</span></a>
						<a href="/admin/lisWithdraw.do" class="active"><img src="/images/cooking/arrow_right.png"><span>인출</span></a>
					</div>
					
					<!-- 제목 -->
					<h1>인출</h1>
					<div class="bar">
						<div class="tab">
							<ul>
								<li class="btn_tab"><a href="" data-tab="" class="active"><span>전체</span></a></li>
							</ul>
						</div>
					</div>
		
					<!-- 목록 -->
					<table class="table text-nowrap mb-0 align-middle fixed-table">
					    <thead class="text-dark fs-4">
					        <colgroup>
					            <col style="width:8%">
					            <col style="width:12%">
					            <col style="width:15%">
					            <col style="width:15%">
					            <col style="width:10%">
					            <col style="width:10%">
					            <col style="width:10%">
					        </colgroup>
					
					        <!-- 검색/총계 row -->
					        <tr>
					            <th colspan="4" class="total text-start"></th>
					            <th colspan="3" class="text-end">
					                <form class="search position-relative text-end">
					                    <input type="text" class="form-control search-chat py-2 ps-5" id="text-srh" placeholder="검색어를 입력하세요">
					                    <i class="ti ti-search position-absolute top-50 start-0 translate-middle-y fs-6 text-dark ms-3"></i>
					                </form>
					            </th>
					        </tr>
					
					        <!-- 컬럼명 row -->
					        <tr class="table_th">
					            <th class="sort_btn" data-key="id">
					                <h6 class="fs-4 fw-semibold mb-0">No</h6>
					            </th>
					            <th>
					                <h6 class="fs-4 fw-semibold mb-0">출금 요청자</h6>
					            </th>
					            <th>
					                <h6 class="fs-4 fw-semibold mb-0">출금 요청일</h6>
					            </th>
					            <th>
					                <h6 class="fs-4 fw-semibold mb-0">요청 금액</h6>
					            </th>
					            <th>
					                <h6 class="fs-4 fw-semibold mb-0">승인여부</h6>
					            </th>
					            <th>
					                <h6 class="fs-4 fw-semibold mb-0">기타</h6>
					            </th>
					            <th>
					                <h6 class="fs-4 fw-semibold mb-0">관리</h6>
					            </th>
					        </tr>
					    </thead>
					
					    <tbody class="guide_list"></tbody>
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
		
		<!-- 가이드 수정 모달 -->
		<%@include file="../admin/modal/editGuideModal.jsp" %>
		<%@include file="../admin/modal/writeGuideModal.jsp" %>
	</body>

</html>