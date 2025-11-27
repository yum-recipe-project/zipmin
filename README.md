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
    <a href="https://github.com/yum-recipe-project/zipmin/wiki">개발 노트</a>
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

<br/><br/>

### 1.2&nbsp;&nbsp;배포 정보

#### 배포 URL

* **[웹사이트 바로가기 →](http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586)**
* **[관리자사이트 바로가기 →](http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586/admin/login.do)**

#### 톄스트 계정

| 구분 | ID | PW |
|:--:|:--:|:--:|
| 총관리자 | admin | 1234 |
| 관리자 | admin1 | 1234 |
| 일반사용자 | user1 | 1234 |
| 일반사용자 | user2 | 1234 |

<br/><br/>

### 1.3&nbsp;&nbsp;개발 정보

* **프로젝트 기간**
  * 1차 개발 기간 : 2025.02.07 – 04.20
  * 2차 개발 기간 : 2025.07.01 - 10.25
  * 운영 기간 : 2025.11.10 - 진행중 (현재 Docker와 Jenkins 기반 배포 환경 구축 준비중)
* **개발 인원**
  * 총 2명 (풀스택)
* **주요 기술**
  * Backend : Java, Spring Boot, Spring Data JPA, Spring Security
  * Frontend : HTML, CSS, Javascript
  * Database : Oracle
  * DevOps : Linux, Tomcat, Docker, Jenkins, AWS EC2

<br/><br/><br/>

## 2. 주요 기능

### 2.1&nbsp;&nbsp;주요 기능

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

### 2.2 화면 설계



<br/><br/><br/>

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

<br/><br/>

### 3.2 아키텍처

집밥의민족 프로젝트는 다음과 같이 설계했습니다.

<img src="src/main/resources/static/images/etc/architecture.png">

* 현재 Docker와 Jenkins 기반 배포 환경 구축 준비중

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

### 3.4 프로젝트 문서

#### 기획 문서

* [요구사항 정의서](https://docs.google.com/spreadsheets/d/1i4fixz1ImXhDaZjmS5SP7Q-ORUexCKOBP9Dup4lNG80/edit?gid=2091731534#gid=2091731534)
* [기능 정의서](https://docs.google.com/spreadsheets/d/15osLaZ4JrcE4XLSzfLZqnu1vSc8KB70O_UQdTB3Blc8/edit?gid=860594176#gid=860594176)
* [개발 일정](https://docs.google.com/spreadsheets/d/1zC_YDl9BHkNTQ4XoS8nbUvrYFBoN5bXuEljF49YOYT0/edit?gid=1982592288#gid=1982592288)

#### 설계 문서

* [데이터베이스 정의서](https://docs.google.com/spreadsheets/d/1xUFNAK-GX4h_Sux6nlGifGED0D8Jhv0q6FGXrJnTVNg/edit?gid=0#gid=0)
* [Swagger API 문서](http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586/swagger-ui/index.html)
* [REST API 공통 응답 구조 및 예외 처리 설계](https://github.com/yum-recipe-project/zipmin/wiki/%5Bdesign%5D-REST-API-%EA%B3%B5%ED%86%B5-%EC%9D%91%EB%8B%B5-%EA%B5%AC%EC%A1%B0-%EB%B0%8F-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC-%EC%84%A4%EA%B3%84-v2)
* [Figma 디자인 설계](https://www.figma.com/design/rxx4DlAb31pyvvyXA67uOC/%EC%A7%91%EB%B0%A5%EC%9D%98%EB%AF%BC%EC%A1%B1?node-id=0-1&t=JpnaCJfU8T7OqdvX-0)

#### 컨벤션

* [브랜치 전략 (Git Flow Branch)](https://github.com/yum-recipe-project/zipmin/wiki/%5BConvention%5D-Git-Flow-Branch)
* [커밋 컨벤션](https://github.com/yum-recipe-project/zipmin/wiki/%5BConvention%5D-Commit-Convention)
* [에러 코드 컨벤션](https://github.com/yum-recipe-project/zipmin/wiki/%5BConvention%5D-Error-Code-Convention)

<br/><br/><br/>

## 4. 협업

### 4.1&nbsp;&nbsp;팀원 소개

집밥의민족 프로젝트는 개발자 2명으로 구성된 팀이 개발하였습니다.

|정하림|부다영|
|:---:|:---:|
|<img src="src/main/resources/static/images/jhrchicken.jpeg" width="120" height="120">|<img src="src/main/resources/static/images/budayeong.jpeg" width="120" height="120">|
|풀스택|풀스택|

<br/><br/>

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

<br/><br/><br/>

## 5. 기타

### 5.1 이슈 및 문제 해결

* [@RequestBody DTO 값이 null로 들어오는 문제](https://github.com/yum-recipe-project/zipmin/wiki/%5BIssue%5D-@RequestBody-DTO-%EA%B0%92%EC%9D%B4-null%EB%A1%9C-%EB%93%A4%EC%96%B4%EC%98%A4%EB%8A%94-%EB%AC%B8%EC%A0%9C)
* [JPA에서 댓글을 좋아요 순으로 정렬하기 (@Fomula)](https://github.com/yum-recipe-project/zipmin/wiki/%5BIssue%5D-JPA%EC%97%90%EC%84%9C-%EB%8C%93%EA%B8%80%EC%9D%84-%EC%A2%8B%EC%95%84%EC%9A%94-%EC%88%9C%EC%9C%BC%EB%A1%9C-%EC%A0%95%EB%A0%AC%ED%95%98%EA%B8%B0-(@Fomula))
* [MapStruct Invalid bound statement](https://github.com/yum-recipe-project/zipmin/wiki/%5BIssue%5D-MapStruct-Invalid-bound-statement)
* [Spring Security localhost에서 리디렉션한 횟수가 너무 많습니다](https://github.com/yum-recipe-project/zipmin/wiki/%5BIssue%5D-Spring-Security-localhost%EC%97%90%EC%84%9C-%EB%A6%AC%EB%94%94%EB%A0%89%EC%85%98%ED%95%9C-%ED%9A%9F%EC%88%98%EA%B0%80-%EB%84%88%EB%AC%B4-%EB%A7%8E%EC%8A%B5%EB%8B%88%EB%8B%A4)
* [Spring Security 적용 후 정적 리소스가 다운로드되는 문제](https://github.com/yum-recipe-project/zipmin/wiki/%5BIssue%5D-Spring-Security-%EC%A0%81%EC%9A%A9-%ED%9B%84-%EC%A0%95%EC%A0%81-%EB%A6%AC%EC%86%8C%EC%8A%A4%EA%B0%80-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C%EB%90%98%EB%8A%94-%EB%AC%B8%EC%A0%9C)
* [[deploy] Could not resolve placeholder 'JWT_SECRET' - 환경 변수 누락](https://github.com/yum-recipe-project/zipmin/wiki/%5Bdeploy%5D%5Bissue%5D-Could-not-resolve-placeholder-'JWT_SECRET'-%E2%80%90-%ED%99%98%EA%B2%BD-%EB%B3%80%EC%88%98-%EB%88%84%EB%9D%BD)
* [[deploy] net::ERR_CONNECTION_REFUSED](https://github.com/yum-recipe-project/zipmin/wiki/%5Bdeploy%5D%5Bissue%5D-net::ERR_CONNECTION_REFUSED)
* [[deploy] ORA-00933: SQL command not properly ended](https://github.com/yum-recipe-project/zipmin/wiki/%5Bdeploy%5D%5Bissue%5D-ORA%E2%80%9000933:-SQL-command-not-properly-ended)

<br/><br/>

### 5.2 회고

#### Keep (간직하고 싶은 잘했던 점 혹은 좋았던 점)

* **효율적인 소통과 협업 방식**<br/>
  프로젝트를 진행하면서 팀의 소통 방식이 자유롭고 효율적이라고 느꼈습니다. 대면 회의와 Slack을 함께 할용하며 회의 직후에 핵심 내용을 공유해 정보 누락을 줄였고, 대면 회의가 어려울 때에도 Slack으로 논의 사항을 체계적으로 공유했습니다. 특히 에러가 발생했을 때에는 문제 상황, 추정 원인, 시도한 방법과 결과, 그리고 결론을 정리해 공유했기 때문에 팀원들이 상황을 빠르게 이해하고 대응할 수 있었습니다.  또한 주석과 커밋 메시지를 명확히 작성하고 GitHub Issue, Pull Request, Wiki를 적극적으로 활용해 협업 효율을 높였습니다.

* **새로운 기술을 적용해볼 수 있었다는 점**<br/>
  다양한 기술을 새롭게 학습하고 실제 프로젝트에 적용해볼 수 있었다는 점이 좋았습니다. REST API를 기반으로 백엔드 구조를 설계하고 Swagger로 API 문서를 정리하며 Postman으로 테스트를 진행했습니다. Spring Data JPA로 데이터를 처리하고 MapStruct로 DTO와 엔티티 변환을 자동화했습니다. 또한 Spring Security와 JWT로 인증과 인가를 구현하고 OAuth2를 통해 소셜 로그인 기능을 연동했습니다. 더불어 PortOne 결제 API와 KakaoMap 지도 API를 연동하며 외부 API 기반 기능도 직접 구현해볼 수 있었습니다. 이러한 과정들이 험난했지만 그만큼 개발 역량을 확장하는 데 큰 도움이 되었습니다.

<br/>

#### Problem (아쉬운 점 혹은 어려운 점)

* **소수의 개발자로만 이루어진 팀이었다는 점**<br/>
  집밥의민족 프로젝트는 단 두명의 개발자로만 구성된 소규모 팀이었습니다. 기획이나 디자인 인력 없이 전부 직접 결정해야 했기에 다양한 역할을 경험할 수 있다는 장점이 있었지만, 동시에 디자인 방향을 정하고 화면을 구성하는 과정에서 예상보다 많은 시간이 소요되었습니다. 결과적으로 최종 결과물은 만족스러웠지만 이를 완성하기까지 시행착오가 많아 개발 일정이 더 길어졌던 부분은 아쉬움으로 남습니다. 또한 팀 규모가 작다 보니 협업 과정에서 발생하는 다양한 의견 충돌을 충분히 겪지 못했고 특히 기획이나 디자인처럼 개발자가 아닌 분야와의 협업을 경험하지 못했다는 점도 아쉬웠습니다.

<br/>

#### Try (보완해야 할 점 혹은 시도해볼 만한 것)





<br/><br/>

### 5.3 목표 및 진행 방향

현재 집밥의민족은 모든 핵심 기능 구현을 완료했으며 안정적인 서비스 제공을 위한 디버깅과 전문적인 배포 환경 구축을 진행 중에 있습니다. 주요 목표와 이를 위한 진행 방향은 다음과 같습니다.

* **배포 자동화 및 무중단 운영 환경 구축**
  * Docker와 Jenkins를 활용한 CI/CD 자동 배포 환경 구축 예정
 
* **성능 최적화 및 PageSpeed 87점 → 95점 이상 향상**
  * Redis 기반 캐싱 도입으로 서버 부하 감소 및 응답 속도 개선 예정
  * 이미지 Lazy Loading 적용으로 페이지 초기 로딩 속도 개선 예정
  * 정적 리소스 최소화 및 불필요한 리소스 제거 예정
  * 쿼리 최적화를 통한 서버 응답 지연 개선 예정

* **월간 활성 사용자 100명 및 하루 평균 10건 이상의 사용자 활동 달성**
  * 신규 사용자 유입 증가를 위한 SEO 최적화 예정
  * 쩝쩝박사 등 사용자 참여형 콘텐츠 강화로 재방문율 향상 예정
