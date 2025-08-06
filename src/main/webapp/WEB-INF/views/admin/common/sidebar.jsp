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
					<a class="sidebar-link" href="/admin/button.do" aria-expanded="false">
						<span><i class="ti ti-mood-happy"></i></span>
						<span class="hide-menu">회원관리</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/button.do" aria-expanded="false">
						<span><i class="ti ti-cash"></i></span>
						<span class="hide-menu">인출</span>
					</a>
				</li>
				
				
				
				
				
				
				
				<!-- ti-article /  게시판 -->
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">쩝쩝박사</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/listVote.do" aria-expanded="false">
						<span><i class="ti ti-article"></i></span>
						<span class="hide-menu">투표</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/alert.do" aria-expanded="false">
						<span><i class="ti ti-article"></i></span>
						<span class="hide-menu">매거진</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/alert.do" aria-expanded="false">
						<span><i class="ti ti-article"></i></span>
						<span class="hide-menu">이벤트</span>
					</a>
				</li>
				
				
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">-</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/alert.do" aria-expanded="false">
						<span><i class="ti ti-alert-circle"></i></span>
						<span class="hide-menu">Alerts</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/card.do" aria-expanded="false">
						<span><i class="ti ti-cards"></i></span>
						<span class="hide-menu">Card</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/form.do" aria-expanded="false">
						<span><i class="ti ti-file-description"></i></span>
						<span class="hide-menu">Forms</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/typography.do" aria-expanded="false">
						<span><i class="ti ti-typography"></i></span>
						<span class="hide-menu">Typography</span>
					</a>
				</li>
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">AUTH</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/login.do" aria-expanded="false">
						<span><i class="ti ti-login"></i></span>
						<span class="hide-menu">Login</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/register.do" aria-expanded="false">
						<span><i class="ti ti-user-plus"></i></span>
						<span class="hide-menu">Register</span>
					</a>
				</li>
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">EXTRA</span>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/icon.do" aria-expanded="false">
						<span><i class="ti ti-mood-happy"></i></span>
						<span class="hide-menu">Icons</span>
					</a>
				</li>
				<li class="sidebar-item">
					<a class="sidebar-link" href="/admin/sample.do" aria-expanded="false">
						<span><i class="ti ti-aperture"></i></span>
						<span class="hide-menu">Sample Page</span>
					</a>
				</li>
			</ul>
			<div class="unlimited-access hide-menu bg-light-primary position-relative mb-7 mt-5 rounded">
				<div class="d-flex">
					<div class="unlimited-access-title me-3">
						<h6 class="fw-semibold fs-4 mb-6 text-dark w-85">Upgrade to pro</h6>
						<a href="https://adminmart.com/product/modernize-bootstrap-5-admin-template/" target="_blank" class="btn btn-primary fs-2 fw-semibold lh-sm">Buy Pro</a>
					</div>
					<div class="unlimited-access-img">
						<img src="../assets/images/backgrounds/rocket.png" alt="" class="img-fluid">
					</div>
				</div>
			</div>
		</nav>
		<!-- End Sidebar navigation -->
	</div>
	<!-- End Sidebar scroll-->
</aside>
<!--  Sidebar End -->