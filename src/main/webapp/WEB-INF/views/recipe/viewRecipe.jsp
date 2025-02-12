<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/recipe/view-recipe.css">
		<script src="/js/recipe/view-recipe.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="recipe_wrap">
					<div class="recipe_header">
						<!-- 제목 -->
						<h2>마법의 카레 가루</h2>
						
						<!-- 스크랩 버튼 -->
						<div class="save_recipe_btn">
							<button onclick="">
								<img src="/images/recipe/star.png"> 저장
							</button>
						</div>
						
						<!-- 레시피 정보 -->
						<div class="recipe_info">
							<div class="recipe_info_item">
								<img src="/images/recipe/level.png">
								<p>초급</p>
							</div>
							<div class="recipe_info_item">
								<img src="/images/recipe/time.png">
								<p>40분</p>
							</div>
							<div class="recipe_info_item">
								<img src="/images/recipe/spicy.png">
								<p>매움</p>
							</div>
						</div>
						
						<!-- 작성자 -->
						<div class="recipe_writer">
							<a class="nickname" href="">
								<img src="/images/common/black.png">
								아잠만
							</a>
							<c:if test="${ true }">
								<a href="">팔로우</a>
							</c:if>
						</div>
						
						<!-- 소개 -->
						<p class="recipe_introduce">
							이 요리는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고
							지금은 당신에게로 옮겨진 이 요리는 4일 안에 당신 곁을 떠나야 합니다.
							이 요리를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다.
							복사를 해도 좋습니다. 혹 미신이라 하실지 모르겠지만 사실입니다.
						</p>
						
						<!-- 카테고리 -->
						<div class="recipe_category">
							<span>#한식</span>
							<span>#크리스마스</span>
						</div>
						
						<!-- 버튼 -->
						<div class="btn_wrap">
							<button onclick="">
								<img src="/images/recipe/siren.png">
							</button>
							<button onclick="">
								<img src="/images/recipe/youtube.png">
							</button>
							<button onclick="">
								<img src="/images/recipe/print.png">
							</button>
						</div>
					</div>
					
					<div class="recipe_ingredient">
						<!-- 제목 -->
						<h3>재료</h3>
						
						<!-- 장보기메모에 재료 담기 버튼 -->
						<div class="save_ingredient_btn">
							<button onclick="">
								<img src="/images/recipe/sell.png"> 장보기메모에 재료 담기
							</button>
						</div>
						
						<!-- 양 -->
						<div class="recipe_serving">
							<select id="servingInput" name="">
								<option value="serving1">1인분</option>
								<option value="serving2">2인분</option>
								<option value="serving3">3인분</option>
								<option value="serving4">4인분</option>
								<option value="serving5">5인분</option>
							</select>
							<p>레시피의 용량을 단순히 인분에 맞춰 계산한 것이므로, 실제 조리 시 정확하지 않을 수 있습니다.</p>
						</div>
						
						<!-- 재료 표 -->
						<table class="ingredient_list">
							<thead>
								<tr>
									<th width="236px">재료</th>
									<th width="236px">용량</th>
									<th width="472px">비고</th>
								</tr>
							</thead>
							<tbody>
								<%-- <c:forEach> --%>
									<tr>
										<td>마늘</td>
										<td>5쪽</td>
										<td>슬라이스</td>
									</tr>
									<tr>
										<td>카레 가루</td>
										<td>2큰술</td>
										<td></td>
									</tr>
									<tr>
										<td>치킨스탁</td>
										<td>7ea</td>
										<td>태태락치킨다시다 50배 희석</td>
									</tr>
								<%-- </c:forEach> --%>
							</tbody>
						
						</table>
					</div>
					
					<div class="recipe_step">
						<!-- 제목 -->
						<h3>조리 순서</h3>
						<ul class="step_list">
							<%-- <c:forEach> --%>
								<li>
									<div class="description">
										<h5>STEP1</h5>
										<p>카레가루를 냄비에 부어버립니다</p>
									</div>
									<c:if test="${ true }">
										<div class="image">
											<img src="/images/common/black.png">
										</div>
									</c:if>
								</li>
							<%-- </c:forEach> --%>
						</ul>
						
					</div>
					
					<!-- 요리팁 -->
					<div class="recipe_tip">
						<!-- 제목 -->
						<h3>주의사항</h3>
						<p>마법의 카레 가루는 이틀 이내에 모두 먹어야 합니다.</p>
					</div>
				</div>
				
				<!-- 탭 메뉴 버튼 -->
				<div class="tab_button_wrap">
					<div class="tab_button">
						<button class="active">리뷰 (3,432)</button>
						<button>댓글 (27)</button>
						<button>후원하기</button>
					</div>
				</div>
				
				<!-- 리뷰 -->
				<div class="tab_content">
					<div class="review_wrap">
						<!-- 리뷰 요약 -->
						<div class="review_summary">
						<button class="write_review_btn" type="button" 
						        data-bs-toggle="modal" 
						        data-bs-target="#writeReviewModal"
						        onclick="">
						    <span>리뷰 작성하기</span>
						</button>
							
						</div>
					
						<!-- 리뷰 헤더 -->
						<div class="review_header">
							<div></div>
							<div class="review_order">
								<button class="active">최신순</button>
								<button>도움순</button>
							</div>
						</div>
						
						<!-- 리뷰 목록 -->
						<c:if test="${ true }">
							<ul class="review_list">
								<!-- 리뷰를 돌면서 original idx랑 comment idx랑 일치하면 -->
								<%-- <c:foreach> --%>
									<c:if test="${ true }">
										<li>
											<img src="/images/common/black.png">
											<div class="review">
												<!-- 리뷰 헤더 -->
												<div class="review_header">
													<div class="review_writer">
														<span>아잠만</span>
														<span>2025.02.11</span>
													</div>
													<c:if test="${ true }">
														<div class="review_action">
															<a href="">수정</a>
															<a href="">삭제</a>
														</div>
													</c:if>
												</div>
												<!-- 리뷰 별점 -->
												<div class="review_score">
													<div class="star">
														<%-- <c:forEach> --%>
															<img src="/images/recipe/star_full.png">
														<%-- </c:forEach> --%>
														<%-- <c:forEach> --%>
															<img src="/images/recipe/star_empty.png">
														<%-- </c:forEach> --%>
													</div>
													<p>3</p>
												</div>
												<!-- 리뷰 내용 -->
												<p class="review_content">
													녹차 아이스크림은 배스킨라빈스가 최고입니다
												</p>
												<!-- 리뷰 좋아요 버튼 -->
												<div class="like_review_btn">
													<button onclick="">
														<c:if test="${ false }">
															<img src="/images/recipe/thumb_up_full.png">
														</c:if>
														<c:if test="${ true }">
															<img src="/images/recipe/thumb_up_empty.png">
														</c:if>
														<p>5</p>
													</button>
													<p>이 리뷰가 도움이 되었다면 꾹!</p>
												</div>
											</div>
										</li>
									</c:if>
								<%-- </c:foreach> --%>
							</ul>
						</c:if>
						
						<div class="more_btn">
							<button>
								<span>더보기</span>
								<img src="/images/recipe/arrow_down_black.png">
							</button>
						</div>
						
					
					</div>
				</div>
				
				<!-- 댓글 -->
				<div class="tab_content">
					<div class="comment_wrap">
						<!-- 댓글 헤더 -->
						<div class="comment_header">
							<div class="comment_count">
								<span>댓글</span>
								<span>7</span>
							</div>
							<div class="comment_order">
								<button class="active">최신순</button>
								<button>오래된순</button>
							</div>
						</div>
						
						<!-- 댓글 작성 -->
						<div class="comment_write">
							<!-- 로그인 하지 않은 경우 -->
							<c:if test="${ false }">
								<a href="/member/login.do">
									<span>댓글 작성을 위해 로그인을 해주세요.</span>
									<span>400</span>
								</a>
							</c:if>
							<!-- 로그인 한 경우 -->
							<c:if test="${ true }">
								<form>
									<div class="login_user">
										<img src="/images/common/black.png">
										<span>아잠만</span>
									</div>
									<div class="comment_input">
										<textarea id="commentInput" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
										<span>400</span>
									</div>
									<div class="write_btn">
										<button type="submit" id="commentButton" class="disable" onclick="" disabled>작성하기</button>
									</div>
								</form>
							</c:if>
						</div>
						
						<!-- 댓글 목록 -->
						<c:if test="${ true }">
							<ul class="comment_list">
								<!-- 댓글을 돌면서 original idx랑 comment idx랑 일치하면 -->
								<%-- <c:foreach> --%>
									<c:if test="${ true }">
										<li>
											<!-- 댓글 헤더 -->
											<div class="comment_header">
												<div class="comment_writer">
													<img src="/images/common/black.png">
													<span>아잠만</span>
													<span>2025.02.11</span>
												</div>
												<c:if test="${ true }">
													<div class="comment_action">
														<a href="">수정</a>
														<a href="">삭제</a>
													</div>
												</c:if>
											</div>
											<!-- 댓글 내용 -->
											<p class="comment_content">
												녹차 아이스크림은 배스킨라빈스가 최고입니다
											</p>
											<!-- 대댓글 쓰기 버튼 -->
											<a class="write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#subcommentWriteModal"
												onclick="openSubcommentWriteModal();">
												<span>답글 쓰기</span>
											</a>
										</li>
									</c:if>
									
									<!-- 대댓글 -->
									<ul class="subcomment_list">
										<!-- 댓글 테이블을 돌면서 orginal idx랑 comment idx랑 다르고 (=대댓글이고) && 이 댓글에 해당하는 대댓글이면 -->
										<%-- <c:forEach> --%>
											<c:if test="${ true }">
												<li>
													<img src="/images/chompessor/arrow_right.png">
													<div class="subcomment">
														<!-- 대댓글 헤더 -->
														<div class="subcomment_header">
															<div class="subcomment_writer">
																<img src="/images/common/black.png">
																<span>아잠만</span>
																<span>2025.02.11</span>
															</div>
															<c:if test="${ true }">
																<div class="subcomment_action">
																	<a href="">수정</a>
																	<a href="">삭제</a>
																</div>
															</c:if>
														</div>
														<!-- 대댓글 내용 -->
														<p class="subcomment_content">
															나뚜르가 최곤데 뭘 모르시네요
														</p>
													</div>
												</li>
											</c:if>
										<%-- </c:forEach> --%>
									</ul>
								<%-- </c:foreach> --%>
							</ul>
						</c:if>
						
						<div class="more_btn">
							<button>
								<span>더보기</span>
								<img src="/images/recipe/arrow_down_black.png">
							</button>
						</div>
					</div>
				</div>
				
				<!-- 후원하기 -->
				<div class="tab_content">
					<div class="support_wrap">
						<h2>리뷰</h2>
					
					</div>
				</div>
				
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>