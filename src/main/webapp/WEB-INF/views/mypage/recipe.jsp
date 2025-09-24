<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>집밥의민족</title>
    <%@include file="../common/head.jsp" %>
    <link rel="stylesheet" href="/css/mypage/recipe.css">
    <script src="/js/mypage/recipe.js"></script>
</head>
<body>
    <%@include file="../common/header.jsp" %>

    <main id="container">
        <div class="content">
            <div class="recipe_wrap">
                
                <!-- 레시피 제목 -->
                <div class="recipe_title">
                    <a href="/mypage.do">
                        <span>
                            마이페이지
                            <img src="/images/mypage/arrow_right.png">
                        </span>
                    </a>
                    <h2>내 레시피</h2>
                </div>

                <!-- 레시피 개수 -->
                <div class="recipe_count">
                    <span id="recipeCount"></span>
                </div>

                <!-- 레시피 목록 (JS에서 동적 생성) -->
                <ul class="recipe_list" id="myRecipeList"></ul>

                <!-- 더보기 버튼 -->
                <div class="more_btn">
                    <button class="btn_more">
                        <span>더보기</span>
                        <img src="/images/mypage/arrow_down.png">
                    </button>
                </div>

            </div>
        </div>
    </main>

    <%@include file="../common/footer.jsp" %>
</body>
</html>
