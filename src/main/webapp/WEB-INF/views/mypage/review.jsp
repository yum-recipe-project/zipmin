<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>집밥의민족 - 내 리뷰</title>
    <%@ include file="../common/head.jsp" %>
    <link rel="stylesheet" href="/css/common/review.css">
    <link rel="stylesheet" href="/css/mypage/review.css">
    <script src="/js/mypage/review.js" defer></script>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <main id="container">
        <div class="content">
            <div class="myreview_wrap">
                <!-- 내 리뷰 헤더 -->
                <div class="myreview_header">
                    <a href="/mypage.do">
                        <span>
                            마이페이지
                            <img src="/images/mypage/arrow_right.png">
                        </span>
                    </a>
                    <h2>내 리뷰</h2>
                </div>

                <!-- 내 리뷰 개수 -->
                <div class="myreview_count">
                    <span></span>
                </div>

                <!-- 내 리뷰 목록 (JS에서 렌더링) -->
                <ul class="myreview_list"></ul>

                <!-- 더보기 버튼 -->
                <div class="more_review_btn">
                    <button class="btn_more">
                        <span>더보기</span>
                        <img src="/images/mypage/arrow_down.png">
                    </button>
                </div>
            </div>
        </div>

        <!-- 리뷰 수정 모달 -->
        <%@ include file="../modal/editReviewModal.jsp" %>

    </main>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>
