<a id="readme-top"></a>


<!-- 프로젝트 로고 -->
<div align="center">
  <!-- <img src="src/main/resources/static/images/logo.png" alt="Logo" width="160" height="70"> -->
  <h3 align="center">집밥의민족</h3>
  <p align="center">
    집밥 레시피 공유 웹사이트
    <br />
    <a href="http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586"><strong>웹사이트 바로가기 »</strong></a>
    <br />
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template">API 명세</a>
    &middot;
    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    &middot;
    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>


## 1. 프로젝트 정보

### 1.1&nbsp;&nbsp;프로젝트 개요

집밥의민족은 집밥 레시피 공유 웹사이트입니다.

집밥의민족은 요리에 익숙하지 않아도 부담 없이 집밥을 요리하도록 돕기 위한 목적에서 출발하였숩니다. 다양한 검색 기능으로 레시피를 쉽게 찾고 장보기 메모와 냉장고 파먹기로 편리하게 요리를 준비합니다. 쩝쩝박사에서는 요리에 대한 다양한 주제로 소통하고, 키친가이드와 쿠킹클래스에서 주방 꿀팁을 배우고 요리 경험을 나눕니다. 집밥의민족은 단순한 레시피 공유를 넘어 일상 속 식습관과 요리 문화를 함께 만들어가는 공간입니다.

집밥의민족은 다음과 같은 주요 기능들을 제공합니다.

1. 다양한 검색 기능으로 레시피를 쉽게 찾아 댓글과 리뷰를 남기고 후원할 수 있습니다.
2. 쩝쩝박사에서는 재미있는 투표와 매거진을 통해 요리 트렌드와 음식 취향에 대한 이야기를 나누고 유쾌한 설전을 벌입니다.
3. 키친가이드와 쿠킹클래스로 주방 꿀팁과 조리법을 배울 수 있습니다.
4. 장보기 메모와 냉장고 파먹기 그리고 지도 기능으로 주변 마트를 찾고 냉장고 속 재료를 관리할 수 있습니다.

이제 <a href="http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586">여기를 클릭하여</a> 집밥의민족을 시작해보세요!

<br/>

### 1.2&nbsp;&nbsp;사용 가이드

#### 배포 URL

- 웹사이트: http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586
- 관리자사이트: http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586/admin/login.do

#### Test ID/PW

- 일반사용자: user / 1234
- 슈퍼관리자: superadmin / 1234
- 관리자: admin1 / 1234

<br/>

### 1.3&nbsp;&nbsp;개발 일정 및 인원

- 프로젝트 개발 인원: 개발자 2인
- 프로젝트 1차 개발 기간: 2025.02.07 - 04.20
- 프로젝트 2차 개발 기간: 2025.07.01 - 10.25

<br/>

### 1.3&nbsp;&nbsp;팀원 및 역할 소개

집밥의민족 프로젝트는 개발자 2명으로 구성된 팀이 개발하였습니다.



<details>
<summary><a href="https://github.com/jhrchicken">펼쳐서 세부 내용을 확인할 수 있습니다.</a></summary>

- 역할: 백엔드 · 프론트엔드(Full-stack)
- 개발 기간: 2025.02.07 – 진행중
- 주요 담당
  - 인증/권한: 로그인·회원가입, JWT(액세스/리프레시), 소셜로그인, 전역 예외처리, ApiResponse 공통 구조
  - 레시피: 레시피 작성/조회, 검색, 정렬·페이지네이션, 댓글·리뷰·후원
  - 쩝쩝박사: 투표·매거진, 참여형 콘텐츠(투표·통계), 관리자용 승인/노출 관리
  - 키친가이드: 조리법·재료 활용 가이드, 콘텐츠 관리
  - 쿠킹클래스: 클래스 등록/신청/승인, 출석/결석 집계, 마이페이지 내 이력 조회
  - 냉장고 파먹기: 재료 관리, 소진·유통기한 기반 추천, 장보기 메모
  - 공통/홈: 헤더/배너, 홈 피드(레시피·가이드), 지도 기반 마트 찾기
- 기술 스택
  - Backend: Java, Spring Boot, Spring Security, JPA, MapStruct
  - DB: Oracle 18c XE
  - Frontend: HTML/CSS/JavaScript, Bootstrap, 일부 React
  - Infra: Linux, AWS EC2, Nginx/Tomcat
- 깃허브: https://github.com/여기에_깃허브_아이디

</details>



<br/><br/>

### 2. 기술 스택



<br/><br/>

### 3. 주요 기능

홈 (메인)

#### 로그인 및 회원가입

- SMTP로 이메일로 비밀번호 변경 링크 전송을 통한 비밀번호 찾기 기능
- 스프링 시큐리티 어쩌구
- AJAX 비동기 방식으로 사용자 닉네임 랜덤 추천
- 소셜 로그인 네이버 구글
- 엑세스 리프레시 토큰 어쩌구

<br/>

#### 홈

- 룰렛 기능을 통한 







<br/>

아키텍처


<br/>

프로젝트 구조


<br/>

기타


<br/>

개발문서 -> 이거 아니면 위에 아키텍처랑 합쳐서 설계로 만들어도 될듯




### 이슈 및 문제해결





프로젝트 신경 쓴 부분
개발적인 면이나 기획적인 면 컨벤션 등등


