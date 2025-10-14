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
				<form method="post" id="openClassForm" name="openClassForm" onsubmit="return validateOpenClassForm(this);">
					<div class="class_header">
						<h2>쿠킹클래스 개설 신청</h2>
					</div>
					
					<div class="agree_wrap">
						<div class="agree">
							<div class="agree_title">개인정보 수집 및 이용 동의</div>
							<div class="agree_box">
								<p>
									본인은 쿠킹 클래스 개설 신청과 관련하여 다음과 같은 개인정보를 제공하며, 이에 대한 수집 및 이용에 동의합니다.<br/>
									1) 수집하는 개인정보 항목:<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;성명, 연락처(전화번호, 이메일)<br/>
									2) 수집 및 이용 목적:<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;쿠킹 클래스 개설 신청 접수 및 관리<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;수업 일정 및 관련 안내 제공<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;정산 및 행정 처리<br/>
									3) 보유 및 이용 기간:<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;클래스 종료 후 1년간 보관 후 파기<br/>
									본인은 위 내용을 충분히 숙지하였으며, 이에 동의합니다.
								</p>
							</div>
							<div class="accept_notice">
								<div class="checkbox_wrap">
									<input type="checkbox" id="acceptNotice1" name="" value="">
									<label for="acceptNotice1">유의사항을 모두 확인했으며, 동의합니다.<span>(필수)</span></label>
								</div>
							</div>
						</div>
						<div class="agree">
							<div class="agree_title">안전 수칙 및 책임 동의서</div>
							<div class="agree_box">
								<p>
									본인은 쿠킹 클래스 개설 및 운영과 관련하여 아래 사항을 준수할 것에 동의합니다.<br/>
									1) 안전 규정 준수<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;강의 중 발생할 수 있는 화재, 화상, 칼 사용 등의 안전사고 예방을 위해 주의를 기울이겠습니다.<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;강의실 내 안전 장비(소화기 등)의 위치 및 사용법을 숙지하겠습니다.<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;개인 부주의로 인한 사고 발생 시, 본인의 책임임을 인정합니다.<br/>
									2) 위생 및 시설 사용 규정 준수<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;주방 시설 및 조리 도구를 깨끗하게 사용하고, 수업 후 정리·정돈을 철저히 하겠습니다.<br/>
									&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;식재료 관리 및 위생 수칙을 준수하며, 위생적인 환경을 유지하겠습니다.<br/>
									본인은 위 내용을 충분히 숙지하였으며, 이에 동의합니다.
								</p>
							</div>
							<div class="accept_notice">
								<div class="checkbox_wrap">
									<input type="checkbox" id="acceptNotice2" name="" value="">
									<label for="acceptNotice2">유의사항을 모두 확인했으며, 동의합니다.<span>(필수)</span></label>
								</div>
							</div>
						</div>
					</div>
					
					<div class="class_wrap">
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
												<input maxlength="50" name="title" placeholder="제목을 입력해주세요" type="text" value="">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">카테고리<span class="ess"></span></th>
										<td>
											<span class="form_text">
												<input maxlength="50" name="category" placeholder="카테고리 입력해주세요" type="text" value="">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">장소<span class="ess"></span></th>
										<td>
											<span class="form_text">
												<input maxlength="50" name="place" placeholder="장소를 입력해주세요" type="text" value="">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">날짜<span class="ess"></span></th>
										<td>
											<span class="form_datepicker">
												<input type="text" class="form-control" id="eventdate" name="eventdate" placeholder="날짜를 선택하세요">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">시간<span class="ess"></span></th>
										<td>
											<span class="form_timepicker">
												<input type="text" class="form-control" id="starttime" placeholder="시작 시간을 선택하세요">
											</span>
											&nbsp;&nbsp;-&nbsp;&nbsp;
											<span class="form_timepicker">
												<input type="text" class="form-control" id="endtime" placeholder="종료 시간을 선택하세요">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">모집 인원<span class="ess"></span></th>
										<td>
											<span class="form_text">
												<input maxlength="50" name="headcount" placeholder="모집 인원을 입력해주세요" type="text" value="">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">신청 마감일<span class="ess"></span></th>
										<td>
											<span class="form_datepicker">
												<input type="text" class="form-control" id="noticedate" name="noticedate" placeholder="날짜를 선택하세요">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">준비물<span class="ess"></span></th>
										<td>
											<span class="form_text">
												<input maxlength="50" name="need" placeholder="준비물을 입력해주세요" type="text" value="">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">대표 이미지<span class="ess"></span></th>
										<td>
											<label class="form_file">
												<input type="file" name="image_url" id="imageInput">
												<input type="text" readonly="readonly" id="fileName" placeholder="파일을 선택하세요">
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
												<input maxlength="50" name="target1" placeholder="추천 대상을 입력해주세요" type="text" value="">
											</span>
											<span class="form_text">
												<input maxlength="50" name="target2" placeholder="추천 대상을 입력해주세요 (선택)" type="text" value="">
											</span>
											<span class="form_text">
												<input maxlength="50" name="target3" placeholder="추천 대상을 입력해주세요 (선택)" type="text" value="">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">클래스 소개<span class="ess"></span></th>
										<td>
											<span class="form_textarea">
												<textarea cols="5" maxlength="500" id="introduce" placeholder="클래스 소개를 작성해주세요" rows="10"></textarea>
											</span>
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<h3>
							커리큘럼
							<span id="addSchedule">
								<img src="/images/cooking/add_circle.png">
								추가하기
							</span>
						</h3>
						<div class="class_intro" id="classSchedule">
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
												<input type="text" class="form-control" name="starttime1" id="starttime_1" placeholder="시작 시간을 선택하세요">
											</span>
											&nbsp;&nbsp;-&nbsp;&nbsp;
											<span class="form_timepicker">
												<input type="text" class="form-control" name="endtime1" id="endtime_1" placeholder="종료 시간을 선택하세요">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">제목<span class="ess"></span></th>
										<td>
											<span class="form_text">
												<input maxlength="50" name="title1" placeholder="수업 제목을 입력해주세요" type="text" value="">
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
												<input maxlength="50" name="name" placeholder="이름을 입력해주세요" type="text" value="">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">경력<span class="ess"></span></th>
										<td>
											<span class="form_text">
												<input maxlength="50" name="career1" placeholder="경력 및 자격증을 입력해주세요" type="text" value="">
											</span>
											<span class="form_text">
												<input maxlength="50" name="career2" placeholder="경력 및 자격증을 입력해주세요 (선택)" type="text" value="">
											</span>
											<span class="form_text">
												<input maxlength="50" name="career3" placeholder="경력 및 자격증을 입력해주세요 (선택)" type="text" value="">
											</span>
										</td>
									</tr>
									<tr>
										<th scope="col">강사 사진<span class="ess"></span></th>
										<td>
											<label class="form_file">
												<input type="file" name="teacher_img" id="teacherImgInput">
												<input type="text" readonly="readonly" placeholder="파일을 선택하세요" id="teacherFileName">
											</label>
										</td>
									</tr>
								</tbody>
							</table>
							<div class=""></div>
						</div>
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