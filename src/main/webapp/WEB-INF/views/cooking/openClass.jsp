<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/cooking/open-class.css">
		<script src="/js/cooking/open-class.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<form id="openClassForm" name="openClassForm">
					<div class="class_header">
						<h2>쿠킹클래스 개설 신청</h2>
					</div>
					
					<div class="agree_wrap">
						<div class="agree">
							<div class="agree_title">개인정보 수집 및 이용 동의</div>
							<div class="agree_box">
								<p>
									어쩌구<br/>
									어쩌구<br/>
									본인은 위 내용을 충분히 이해하였으며, 이에 동의합니다.
								</p>
							</div>
							<!-- 여기에 체크박스 -->
							<div class="accept_notice">
								<div class="checkbox_wrap">
									<input type="checkbox" id="acceptNotice1" name="" value="">
									<label for="acceptNotice1">유의사항을 모두 확인했으며, 동의합니다.<span>(필수)</span></label>
								</div>
							</div>
						</div>
						<div class="agree">
							<div class="agree_title">개인정보 수집 및 이용 동의</div>
							<div class="agree_box">
								<p>
									어쩌구<br/>
									어쩌구<br/>
									어쩌구<br/>
									어쩌구<br/>
									어쩌구<br/>
									어쩌구<br/>
									어쩌구<br/>
									본인은 위 내용을 충분히 이해하였으며, 이에 동의합니다.
								</p>
							</div>
							<!-- 여기에 체크박스 -->
							<div class="accept_notice">
								<div class="checkbox_wrap">
									<input type="checkbox" id="acceptNotice2" name="" value="">
									<label for="acceptNotice2">유의사항을 모두 확인했으며, 동의합니다.<span>(필수)</span></label>
								</div>
							</div>
						</div>
					</div>
					
					<div class="class_wrap">
						<form id="openClassForm" name="openClassForm" method="post">
							<h3>기본 내용</h3>
							<div class="class_info">
								<p class="rt_note">필수입력사항</p>
								<table>
									<colgroup>
										<col width="130px">
										<col width="*">
									</colgroup>
									<tbody>
										<tr>
											<th scope="col">제목<span class="ess"></span></th>
											<td>
												<span class="form_text">
													<input maxlength="50" name="titleInput" placeholder="제목을 입력해주세요" type="text" value="">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">장소<span class="ess"></span></th>
											<td>
												<span class="form_text">
													<input maxlength="50" name="venueInput" placeholder="장소를 입력해주세요" type="text" value="">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">날짜<span class="ess"></span></th>
											<td>
												<span class="form_datepicker">
													<input type="text" class="form-control" id="datepicker">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">시간<span class="ess"></span></th>
											<td>
												<span class="form_timepicker">
													<input type="text" class="form-control" id="starttimepicker">
												</span>
												-
												<span class="form_timepicker">
													<input type="text" class="form-control" id="endtimepicker">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">모집 인원<span class="ess"></span></th>
											<td>
												<span class="form_text">
													<input maxlength="50" name="headcountInput" placeholder="모집 인원을 입력해주세요" type="text" value="">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">준비물<span class="ess"></span></th>
											<td>
												<span class="form_text">
													<input maxlength="50" name="bagInput" placeholder="준비물을 입력해주세요" type="text" value="">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">대표 이미지<span class="ess"></span></th>
											<td>
												<label class="form_file">
													<input type="file">
													<input type="text" readonly="readonly">
												</label>
											</td>
										</tr>
										
									</tbody>
								</table>
							
							</div>
							<h3>클래스 소개</h3>
							<div class="class_intro">
								<p class="rt_note">필수입력사항</p>
								<table>
									<colgroup>
										<col width="130px">
										<col width="*">
									</colgroup>
									<tbody>
										<tr>
											<th scope="col">추천 대상<span class="ess"></span></th>
											<td>
												<span class="form_text">
													<input maxlength="50" name="targetInput" placeholder="추천 대상을 입력해주세요" type="text" value="">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">클래스 소개<span class="ess"></span></th>
											<td>
												<span class="form_textarea">
													<textarea cols="5" maxlength="500" id="contentInput" placeholder="내용을 입력해주세요" rows="10"></textarea>
												</span>
											</td>
										</tr>
									</tbody>
								</table>
							</div>

							
							<h3>커리큘럼</h3>
							<div class="class_intro">
								<p class="rt_note">필수입력사항</p>
								<table>
									<colgroup>
										<col width="130px">
										<col width="*">
									</colgroup>
									<tbody>
										<tr>
											<th scope="col">시간<span class="ess"></span></th>
											<td>
												<span class="form_timepicker">
													<input type="text" class="form-control" id="starttimepicker">
												</span>
												-
												<span class="form_timepicker">
													<input type="text" class="form-control" id="endtimepicker">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">제목<span class="ess"></span></th>
											<td>
												<span class="form_text">
													<input maxlength="50" name="titleInput" placeholder="제목을 입력해주세요" type="text" value="">
												</span>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							
							<h3>강사 정보</h3>
							<div class="class_intro">
								<p class="rt_note">필수입력사항</p>
								<table>
									<colgroup>
										<col width="130px">
										<col width="*">
									</colgroup>
									<tbody>
										<tr>
											<th scope="col">이름<span class="ess"></span></th>
											<td>
												<span class="form_text">
													<input maxlength="50" name="nameInput" placeholder="이름을 입력해주세요" type="text" value="">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">경력<span class="ess"></span></th>
											<td>
												<span class="form_text">
													<input maxlength="50" name="careerInput" placeholder="경력 및 자격증을 입력해주세요" type="text" value="">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="col">대표 이미지<span class="ess"></span></th>
											<td>
												<label class="form_file">
													<input type="file">
													<input type="text" readonly="readonly">
												</label>
											</td>
										</tr>
									</tbody>
								</table>
								<div class=""></div>
							</div>
						</form>
					</div>
					
					<div class="btn_wrap">
						<button class="btn_outline">취소</button>
						<button class="btn_primary">신청하기</button>
					</div>
				
				</form>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>