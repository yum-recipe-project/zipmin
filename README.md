<a id="readme-top"></a>

<!-- 프로젝트 로고 -->
<div align="center">
  <img src="src/main/resources/static/images/etc/title.gif" width="160" height="130">
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
    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=enhancement&template=feature-request---.md">개발 노트</a>
  </p>
</div>

<!-- TODO : 이곳에 목차 만들기 -->

<br/><br/><br/>

## 1. 프로젝트 정보

### 1.1&nbsp;&nbsp;프로젝트 개요

집밥의민족은 레시피를 공유하고 요리 경험을 나누는 **레시피 커뮤니티 웹사이트**입니다.<br/>
요리에 익숙하지 않아도 부담 없이 집밥을 요리하도록 돕기 위한 목적에서 출발하였습니다.

다양한 검색 기능으로 레시피를 쉽게 찾고 장보기 메모와 냉장고 파먹기로 편리하게 요리를 준비합니다.<br/>
쩝쩝박사에서는 요리에 대한 다양한 주제로 소통하고, 키친가이드와 쿠킹클래스에서 주방 꿀팁을 배우고 요리 경험을 나눕니다.<br/>
집밥의민족은 단순한 레시피 공유를 넘어 일상 속 식습관과 요리 문화를 함께 만들어가는 공간입니다.

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

#### 톄스트 계정

| 구분 | ID | PW |
|:--:|:--:|:--:|
| 총관리자 | admin | 1234 |
| 관리자 | admin1 | 1234 |
| 일반사용자 | user1 | 1234 |
| 일반사용자 | user2 | 1234 |

<br/>

### 1.3&nbsp;&nbsp;개발 정보

* **프로젝트 기간**
  * 1차 개발 기간 : 2025.02.07 – 04.20
  * 2차 개발 기간 : 2025.07.01 - 10.25
  * 운영 기간 : 2025.11.10 - 진행중 (DevOps 학습 및 유지보수 진행중)
* **개발 인원**
  * 총 2명 (풀스택)
* **주요 기술**
  * Backend : Java, Spring Boot, Spring Data JPA, Spring Security
  * Frontend : HTML, CSS, Javascript
  * Database : Oracle
  * DevOps : Linux, Tomcat, Docker, Jenkins, AWS EC2

<br/><br/>

## 2. 주요 기능

### 2.1&nbsp;&nbsp;세부 주요 기능

#### 홈

* 시간대에 따라 룰렛으로 랜덤 메뉴 추천
* 상단 배너에서 추천 키친가이드 게시글 자동 순환
* 최신·인기 레시피 등 카테고리별 랭킹 조회

#### 인증 및 회원 관리

* 자체 로그인 및 소셜 로그인 지원
* Spring Security 기반 인증 및 인가와 JWT 기반 토큰 관리
* 회원가입 및 로그인과 회원정보 수정을 포함한 계정 관리
* SMTP로 이메일 인증 및 링크 전송을 통한 비밀번호 재설정
* AJAX 비동기 방식으로 사용자 중복 검사와 닉네임 랜덤 추천

#### 레시피

* 검색창·카테고리·정렬을 포함한 레시피 목록 검색
* 레시피 작성 및 레시피 관리
* 제목·소개·난이도·조리시간·맵기 등 레시피 상세 정보 표시
* 인분 수에 따른 재료량 자동 조정 및 장보기 메모 담기 기능 지원
* 리뷰·댓글·스크랩·신고·인쇄·후원·팔로우 등 다양한 사용자 상호작용

#### 키친가이드

* 손질법과 보관법 등 주방 정보를 제공하는 게시판
* 검색창·카테고리·정렬을 포함한 키친가이드 목록 검색과 스크랩 기능 지원
* 관리자의 게시글 작성 및 사용자의 댓글 기능 지원

#### 쩝쩝박사

* 음식 관련 투표·매거진·이벤트 등 참여형 콘텐츠를 제공하는 게시판
* 검색창·카테고리·정렬을 포함한 쩝쩝박사 목록 검색
* 관리자의 게시글 작성 및 사용자의 댓글 기능 지원

#### 쿠킹클래스

* 사용자가 클래스 개설을 신청하면 관리자의 승인 후 개설
* 신청서 작성과 신청자 선정 및 출석 체크 등 쿠킹클래스 운영 기능 지원
* 3회 이상 결석 시 마지막 신청일 기준 3개월 신청 제한 적용
* 검색창·카테고리·정렬·상태 필터를 포함한 클래스 목록 조회
* 준비물·일정·장소·커리큘럼·강사 정보 등 클래스 상세 정보 표시
* 마이페이지에서 개설·신청 클래스 관리

#### 후원

* PortOne 결제 연동으로 포인트 충전 후 레시피 후원
* 출금 계좌를 등록하고 관리
* 출금 신청 후 관리자 출금 처리 및 내역 조회

#### 나의 냉장고

* 식재료를 등록·관리하고 나의 냉장고에 등록
* 냉장고 속 재료 기반 요리 추천 및 일치율 표시
* 장보기 메모 작성 후 장보기 완료 시 재료를 냉장고에 자동 등록
* KakaoMap을 활용한 주변 마트 위치 안내

#### 관리자

* 사용자 계정 조회 및 관리
* 출금 신청 승인 및 정산 내역 관리
* 레시피·클래스·키친가이드·쩝쩝박사 등 게시판 관리
* 쿠킹클래스 승인 및 관리

<br/><br/>

## 3. 프로젝트 설계

### 3.1 사용 기술

|구분 | 사용 기술 |
|:--:|--|
| **Frontend** | HTML5&nbsp;&nbsp;·&nbsp;&nbsp;CSS3&nbsp;&nbsp;·&nbsp;&nbsp;JavaScript&nbsp;&nbsp;·&nbsp;&nbsp;Bootstrap 5.3.3&nbsp;&nbsp;·&nbsp;&nbsp;KakaoMap API |
| **Backend** | Java 21&nbsp;&nbsp;·&nbsp;&nbsp;Spring Boot 3.0.3&nbsp;&nbsp;·&nbsp;&nbsp;Spring Data JPA&nbsp;&nbsp;·&nbsp;&nbsp;Spring Security&nbsp;&nbsp;·&nbsp;&nbsp;JWT&nbsp;&nbsp;·&nbsp;&nbsp;Mapstruct |
| **Database** | Oracle 21C |
| **Infrastructure** | Linux&nbsp;&nbsp;·&nbsp;&nbsp;Apache Tomcat 10.1.46&nbsp;&nbsp;·&nbsp;&nbsp;Docker&nbsp;&nbsp;·&nbsp;&nbsp;Jenkins&nbsp;&nbsp;·&nbsp;&nbsp;AWS EC2 |
| **Tools** | STS 4&nbsp;&nbsp;·&nbsp;&nbsp;GitHub&nbsp;&nbsp;·&nbsp;&nbsp;Figma&nbsp;&nbsp;·&nbsp;&nbsp;Swagger&nbsp;&nbsp;·&nbsp;&nbsp;Postman&nbsp;&nbsp;·&nbsp;&nbsp;SQL Developer&nbsp;&nbsp;·&nbsp;&nbsp;Slack |
| **Etc.** | PortOne&nbsp;&nbsp;·&nbsp;&nbsp;SMTP |

<br/>

### 3.2 아키텍처

<img src="src/main/resources/static/images/etc/architecture.png">

<br/>

### 3.3 프로젝트 구조

집밥의민족 프로젝트는 <b>계층형 구조</b>를 도입하여 비즈니스 로직과 데이터 접근을 명확히 분리하여 개발되었습니다. 계층형 구조를 도입한 이유는 다음과 같습니다.

<ol>
  <li>계층 간 역할과 책임을 명확히 분리하여 코드 재사용성과 유지보수 용이성 확보</li>
  <li>유연한 확장성과 구조적 일관성을 기반으로 협업 효율성 및 시스템 안정성 향상</li>
</ol>

자세한 프로젝트 구조는 아래에서 확인할 수 있습니다.

```properties
Zipmin
├── README.md
├── build.gradle #Gradle 빌드 스크립트
├── gradle
├── gradlew
├── gradlew.bat
├── settings.gradle # Gradle 설정 파일
└── src
    ├── test
    └── main
        ├── java
        │   └── com
        │       └── project
        │           └── zipmin
        │               ├── ZipMinApplication.java
        │               ├── MainController.java # 메인 컨트롤러
        │               ├── ServletInitializer.java
        │               ├── api         # API 응답 디렉터리
        │               ├── config      # 설정 디렉터리
        │               ├── controller  # 컨트롤러 디렉터리
        │               ├── dto         # DTO 디렉터리
        │               ├── entity      # JPA 엔티티 디렉터리
        │               ├── filter      # 필터 디렉터리
        │               ├── handler     # 핸들러 디렉터리
        │               ├── mapper      # MapStruct 기반 매퍼 디렉터리
        │               ├── oauth2      # OAuth2 디렉터리
        │               ├── repository  # 레포지토리 디렉터리
        │               ├── service     # 서비스 디렉터리
        │               ├── swagger     # Swagger 응답 디렉터리
        │               └── util        # 유틸 디렉터리
        ├── resources
        │   ├── application.properties
        │   └── static # 정적 리소스 파일
        │       ├── assets
        │       ├── css
        │       ├── fonts
        │       ├── images
        │       └── js
        └── webapp
            └── WEB-INF
                └── views # JSP 뷰 디렉터리
                    ├── admin  # 기능(도메인) 별 디렉터리
                    ├── ...    # ...
                    └── user   # 기능(도메인) 별 디렉터리
```

<br/>

### 3.4 개발 문서

* 기획 문서

* 설계 문서

* 운영 문서

ERD 다이어그램
Swagger 명세 링크


<br/>

### 4. 협업

### 4.1&nbsp;&nbsp;팀원 소개

집밥의민족 프로젝트는 개발자 2명으로 구성된 팀이 개발하였습니다.

|정하림|부다영|
|:---:|:---:|
|<img src="src/main/resources/static/images/jhrchicken.jpeg" width="120" height="120">|<img src="src/main/resources/static/images/budayeong.jpeg" width="120" height="120">|
|풀스택|풀스택|

<br/>

### 4.2 담당 역할

<details>
  <summary>&nbsp;<b>정하림 담당 역할</b></summary>

  <br/>
  
  **프로젝트 개발 환경 구축**
  * JPA 기반 도메인 구성과 계층형 프로젝트 구조 설계
  * 브랜치 전략과 컨벤션과 API 응답 구조 정의로 일관된 개발 환경 구성
  * Swagger · GitHub Wiki · Google Sheets 기반으로 프로젝트 문서 체계화
  * Slack · GitHub Issues 기반으로 팀 소통 및 업무 관리 효율화

  **데이터베이스 설계**
  * Oracle 기반 ERD를 설계하고 이를 JPA 엔티티로 구현해 도메인 구조 구성
  
  **UI/UX 설계 및 구현**
  * Figma로 주요 화면과 사용자 흐름 설계
  * HTML · CSS · JavaScript 기반 공통 UI 컴포넌트 및 주요 화면 구현 (아래 항목을 열어 캡처 이미지를 확인하실 수 있습니다)

  **인증 및 회원 도메인 개발**
  * Spring Security · JWT 기반으로 자체 로그인 및 OAuth2 소셜 로그인 기능 구현
  * 이중 토큰 구조와 Axios 인터셉터 기반 자동 재발급 구현
  * SMTP로 이메일 인증 및 비밀번호 재설정 기능 구현
  * 회원가입, 로그인, 프로필 수정, 비밀번호 확인, 회원정보 변경 등 계정 관리 기능 구현
  
  **레시피 도메인 개발**
  * 검색과 카테고리와 정렬을 포함한 레시피 목록 조회 및 JPA Page 페이징 처리
  * 복합 입력과 유효성 검증을 포함한 레시피 작성 기능 구현
  * 레시피 상세 조회 및 인쇄 기능 구현
  * 인분 수에 따른 재료량 자동 계산 및 장보기 메모 모달 연동
  
  **쩝쩝박사 도메인 개발**
  * 검색과 카테고리와 정렬을 포함한 쩝쩝박사 목록 조회 및 JPA Page 페이징 처리
  * 투표와 매거진과 이벤트 상세 조회 기능 구현
  * 기간 내 1회 참여와 항목별 비율 계산을 포함한 투표 기능 구현
    
  **쿠킹클래스 도메인 개발**
  * 검색과 카테고리와 상태와 정렬을 포함한 클래스 목록 조회 및 JPA Page 페이징 처리
  * 클래스 조회와 클래스 신청 및 취소 기능 구현
  * 신청서 확인과 선정 및 출석 관리 기능 구현
  * 3회 이상 결석 시 3개월 신청 제한 규칙 적용
  
  **냉장고 도메인 개발**
  * 식재료 등록과 관리 기능 구현
  * 보유 식재료를 나의 냉장고에 담아 관리하는 기능 구현
  * 냉장고 재료 기반 레시피 추천 기능 구현
  
  **댓글 도메인 개발**
  * 정렬을 포함한 댓글과 대댓글 목록 조회 및 더보기 페이징 처리
  * 댓글과 대댓글의 작성과 수정과 삭제 기능 구현
  * 좋아요와 신고 기능 구현
  
  **관리자 페이지 개발**
  * 사용자 계정 조회와 관리 기능 구현
  * 레시피 목록 조회 기능 구현
  * 쩝쩝박사 목록 조회와 관리 기능 구현
  * 쿠킹클래스 목록 조회와 승인을 포함한 관리 기능 구현
  * 댓글 목록 조회와 관리 기능 구현
  
  **배포**
  * AWS EC2 리눅스 환경에 Java · Oracle · Tomcat을 직접 설치해 배포 환경 구축
  * 현재 Docker · Jenkins 기반 자동화 환경 구성 진행중
</details>
<details>
  <summary>&nbsp;<b>부다영 담당 역할</b></summary>

  <br/>

  
</details>

<br/>

## 5. 기타

### 5.1 이슈 및 문제 해결

<br/>

### 5.2 개선 사항
이슈 및 문제해결

### 5.3 프로젝트에서 배운 점


### 5.4 결과 및 확장성

