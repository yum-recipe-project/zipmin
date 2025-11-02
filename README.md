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
    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=bug&template=bug-report---.md">개발문서</a>
    &middot;
    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>

<!-- TODO : 이곳에 목차 만들기 -->

<br/>

## 1. 프로젝트 정보

### 1.1&nbsp;&nbsp;프로젝트 개요

**집밥의민족은 집밥 레시피 공유 웹사이트입니다.**

집밥의민족은 요리에 익숙하지 않아도 부담 없이 집밥을 요리하도록 돕기 위한 목적에서 출발하였습니다. 다양한 검색 기능으로 레시피를 쉽게 찾고 장보기 메모와 냉장고 파먹기로 편리하게 요리를 준비합니다. 쩝쩝박사에서는 요리에 대한 다양한 주제로 소통하고, 키친가이드와 쿠킹클래스에서 주방 꿀팁을 배우고 요리 경험을 나눕니다. 집밥의민족은 단순한 레시피 공유를 넘어 일상 속 식습관과 요리 문화를 함께 만들어가는 공간입니다.

**집밥의민족은 다음과 같은 주요 기능들을 제공합니다.**

1. 다양한 검색 기능으로 레시피를 쉽게 찾아 댓글과 리뷰를 남기고 후원할 수 있습니다.
2. 쩝쩝박사에서는 재미있는 투표와 매거진을 통해 요리 트렌드와 음식 취향에 대한 이야기를 나누고 유쾌한 설전을 벌입니다.
3. 키친가이드와 쿠킹클래스로 주방 꿀팁과 조리법을 배울 수 있습니다.
4. 장보기 메모와 냉장고 파먹기 그리고 지도 기능으로 주변 마트를 찾고 냉장고 속 재료를 관리할 수 있습니다.

**이제 <a href="http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586">여기를 클릭하여</a> 집밥의민족을 시작해보세요!**

<br/>

### 1.2&nbsp;&nbsp;사용 가이드

#### 배포 URL

* **[:globe_with_meridians: 웹사이트 바로가기](http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586)**
* **[:globe_with_meridians: 관리자사이트 바로가기](http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586/admin/login.do)**

#### Test ID/PW

* 일반사용자: user1 / 1234
* 관리자: admin1 / 1234
  
<br/>

### 1.3&nbsp;&nbsp;개발 정보

* 프로젝트 기간: 2025.02.07 – 04.20 & 2025.07.01 - 10.25
* 개발 인원: 2명
* 주요 기술: Java, Spring Boot, Spring Data JPA, Spring Security, REST API, HTML, CSS, Javascript, Oracle, Linux, AWS EC2

<br/>

### 1.4&nbsp;&nbsp;팀원 및 역할 소개

집밥의민족 프로젝트는 개발자 2명으로 구성된 팀이 개발하였습니다.

#### 정하림
* 깃허브 : 
* **역할:** 백엔드 · 프론트엔드 전반 설계 및 구현  
* **담당:** 로그인/회원관리, 레시피, 쩝쩝박사, 쿠킹클래스, 배포(AWS EC2)

&nbsp;&nbsp;<details>
&nbsp;&nbsp;<summary>세부 내용 펼치기</summary>

- JWT 인증 및 회원 관리 구현  
- REST API 공통 응답 구조 및 예외 처리 설계  
- 레시피/쩝쩝박사/쿠킹클래스 CRUD 구현  
- Oracle 18c DB 설계 및 서버 배포  
- 프론트엔드 UI/UX 개선 (JSP, JavaScript)  

&nbsp;&nbsp;</details>



<br/><br/>

### 3. 주요 기능

홈 (메인)

#### 로그인 및 회원가입

- SMTP로 이메일로 비밀번호 변경 링크 전송을 통한 비밀번호 찾기 기능
- 스프링 시큐리티 어쩌구
- AJAX 비동기 방식으로 사용자 닉네임 랜덤 추천
- 소셜 로그인 네이버 구글 및 자체 로그인
- 엑세스 리프레시 토큰 어쩌구

  * SMTP로 비밀번호 재설정 링크를 이메일로 전송하는 비밀번호 찾기 기능
  * Spring Security 기반의 사용자 인증 · 인가 및 세션 관리
  * AJAX 비동기 요청으로 사용자 닉네임·이메일·전화번호 중복 검사 및 닉네임 랜덤 추천 기능
  * OAuth2로 소셜 로그인(Naver, Google)과 자체 로그인 기능 구현
  * JWT 기반 토큰 발급 및 재발급 및
 
  

<br/>

#### 홈

- 룰렛 기능을 통한 





## 3. 프로젝트 설계

### 3.1 사용 기술

|구분 | 사용 기술 |
|:--:|--|
| **Frontend** | HTML5&nbsp;&nbsp;·&nbsp;&nbsp;CSS3&nbsp;&nbsp;·&nbsp;&nbsp;JavaScript&nbsp;&nbsp;·&nbsp;&nbsp;Bootstrap 5.3.3&nbsp;&nbsp;·&nbsp;&nbsp;KakaoMap API |
| **Backend** | Java 21&nbsp;&nbsp;·&nbsp;&nbsp;Spring Boot 3.0.3&nbsp;&nbsp;·&nbsp;&nbsp;Spring Data JPA&nbsp;&nbsp;·&nbsp;&nbsp;Spring Security&nbsp;&nbsp;·&nbsp;&nbsp;JWT&nbsp;&nbsp;·&nbsp;&nbsp;Mapstruct |
| **Database** | Oracle 21C |
| **Infrastructure** | Linux&nbsp;&nbsp;·&nbsp;&nbsp;Apache Tomcat 10.1.46&nbsp;&nbsp;·&nbsp;&nbsp;AWS EC2 |
| **Tools** | STS 4&nbsp;&nbsp;·&nbsp;&nbsp;GitHub&nbsp;&nbsp;·&nbsp;&nbsp;Figma&nbsp;&nbsp;·&nbsp;&nbsp;Swagger&nbsp;&nbsp;·&nbsp;&nbsp;Postman&nbsp;&nbsp;·&nbsp;&nbsp;SQL Developer&nbsp;&nbsp;·&nbsp;&nbsp;Slack |
| **Etc.** | PortOne&nbsp;&nbsp;·&nbsp;&nbsp;SMTP |

<br/>

### 3.2 아키텍처

<br/>

### 3.3 프로젝트 구조


<br/>

#### 3.4 개발 문서
개발문서 -> 이거 아니면 위에 아키텍처랑 합쳐서 설계로 만들어도 될듯




## 4. 기타

### 4.1 이슈 및 문제 해결

<br/>

### 4.2 개선 사항
이슈 및 문제해결

### 4.3 프로젝트에서 배운 점


### 4.4 결과 및 확장성




프로젝트 신경 쓴 부분
개발적인 면이나 기획적인 면 컨벤션 등등


