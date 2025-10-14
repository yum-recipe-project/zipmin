<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<aside class="left-sidebar">
	<div>
		<div class="brand-logo d-flex align-items-center justify-content-between" style="background-color: #5D87FF;">
			<a href="/admin/home.do" class="text-nowrap logo-img">
				<img src="../assets/images/logos/logo.svg" width="180" alt="" />
				<!-- <img src="/images/common/logo.png" width="180" alt="" /> -->
				
			</a>
			<div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
				<i class="ti ti-x fs-8"></i>
			</div>
		</div>
		
		<nav class="sidebar-nav scroll-sidebar" data-simplebar="">
			<ul id="sidebarnav">
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">Home</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/home.do" aria-expanded="false">
						<span>
							<i class="ti ti-layout-dashboard"></i>
						</span>
						<span class="hide-menu">Dashboard</span>
					</a>
				</li>
				
				<!-- 회원 -->
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">회원</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listUser.do" aria-expanded="false">
						<span><i class="ti ti-user-circle"></i></span>
						<span class="hide-menu">회원 관리</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listWithdraw.do" aria-expanded="false">
						<span><i class="ti ti-cash"></i></span>
						<span class="hide-menu">인출</span>
					</a>
				</li>
				
				<!-- ti-article /  게시판 -->
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">게시판</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listRecipe.do" aria-expanded="false">
						<span><i class="ti ti-notebook"></i></span>
						<span class="hide-menu">레시피</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listGuide.do" aria-expanded="false">
						<span><i class="ti ti-tools-kitchen-2"></i></span>
						<span class="hide-menu">키친가이드</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listChomp.do" aria-expanded="false">
						<span><i class="ti ti-chef-hat"></i></span>
						<span class="hide-menu">쩝쩝박사</span>
					</a>
				</li>
				
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">댓글/리뷰</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listComment.do" aria-expanded="false">
						<span><i class="ti ti ti-message-2"></i></span>
						<span class="hide-menu">댓글 관리</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listReview.do" aria-expanded="false">
						<span><i class="ti ti ti-star"></i></span>
						<span class="hide-menu">리뷰 관리</span>
					</a>
				</li>
				
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">쿠킹클래스</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listClass.do" aria-expanded="false">
						<span><i class="ti ti-mood-smile"></i></span>
						<span class="hide-menu">쿠킹클래스 관리</span>
					</a>
				</li>
				
				<!-- 여기에 쿠킹클래스 같은거 들어가야겠지.. -->
		</nav>
		<!-- End Sidebar navigation -->
	</div>
	<!-- End Sidebar scroll-->
</aside>
<!--  Sidebar End -->