; <a href="https://club-project-one.vercel.app/" target="_blank">
; <img src="https://github.com/user-attachments/assets/daa622b9-7c69-4786-8db3-4996b7f140be" alt="배너" width="100%"/>
</a>

<br/>
<br/>


# 1. Project Overview (프로젝트 개요)
- 프로젝트 이름: KOSTA 강사님 팬카페 
- 프로젝트 설명: 침하하를 모티브로 한 강사님과 소통할 수 있는 커뮤니티

<br/>
<br/>

# 2. Team Members (팀원 및 팀 소개)
| 한선우 | 최소진 | 최정아 |
|:------:|:------:|:------:|
| <img src="https://avatars.githubusercontent.com/u/120350053?v=4" alt="한선우" width="150"> | <img src="https://github.com/user-attachments/assets/78ec4937-81bb-4637-975d-631eb3c4601e" alt="신유승" width="150"> | 
|  BE | FE | FE | 
| [GitHub](https://github.com/hamster0410) | [GitHub](https://github.com/SinYusi) | [GitHub](https://github.com/nay3on) | 

<br/>
<br/>

# 3. Key Features (주요 기능)
- **회원가입**:
  - 회원가입 시 DB에 유저정보가 등록됩니다.

- **로그인**:
  - 사용자 인증 정보를 통해 로그인합니다.
  - JWT토큰을 사용해 인증상태를 저장합니다.

- **강사님과 소통**:
  - 카테고리에 맞는 게시글을 올려 통신할 수 있습니다.
  - 댓글과 좋아요를 사용해 소통할 수 있습니다.

- **게시글 찾기**:
  - 검색 시 카테고리별 해당 게시판이 보여집니다.

<br/>
<br/>

# 4. Tasks & Responsibilities (작업 및 역할 분담)
|  |  |  |
|-----------------|-----------------|-----------------|
| 한선우    |  <img src="https://avatars.githubusercontent.com/u/120350053?v=4" alt="이동규" width="100"> | <ul><li>프로젝트 계획 및 관리</li><li>REST API 명세 작성 및 구현</li><li>ERD 설계</li></ul>     |
| 신유승   |  <img src="https://github.com/user-attachments/assets/78ec4937-81bb-4637-975d-631eb3c4601e" alt="신유승" width="100">| <ul><li>메인 페이지 개발</li><li>동아리 만들기 페이지 개발</li><li>커스텀훅 개발</li></ul> |
| 김나연   |  <img src="https://github.com/user-attachments/assets/78ce1062-80a0-4edb-bf6b-5efac9dd992e" alt="김나연" width="100">    |<ul><li>홈 페이지 개발</li><li>로그인 페이지 개발</li><li>동아리 찾기 페이지 개발</li><li>동아리 프로필 페이지 개발</li><li>커스텀훅 개발</li></ul>  |


<br/>
<br/>

# 5. Technology Stack (기술 스택)
## 5.1 Language
|  |  |
|-----------------|-----------------|
| HTML5    |<img src="https://github.com/user-attachments/assets/2e122e74-a28b-4ce7-aff6-382959216d31" alt="HTML5" width="100">| 
| CSS3    |   <img src="https://github.com/user-attachments/assets/c531b03d-55a3-40bf-9195-9ff8c4688f13" alt="CSS3" width="100">|
| Javascript    |  <img src="https://github.com/user-attachments/assets/4a7d7074-8c71-48b4-8652-7431477669d1" alt="Javascript" width="100"> | 
| Java    |  <img src="![java_original_logo_icon_146458](https://github.com/user-attachments/assets/f70aeb2c-38e2-4808-8ba2-cf9c36407925)" alt="Java" width="100"> | 
<br/>

## 5.2 Frotend
|  |  |  |
|-----------------|-----------------|-----------------|
| React    |  <img src="https://github.com/user-attachments/assets/e3b49dbb-981b-4804-acf9-012c854a2fd2" alt="React" width="100"> | 18.3.1    |

<br/>

## 5.3 Backend
|  |  |  |
|-----------------|-----------------|-----------------|
| SpringBoot    |  <img src="![spring-boot-logo](https://github.com/user-attachments/assets/b4c58bff-c35d-4325-9942-df17b9981c02)" alt="springboot" width="100">    | 10.12.5    |
| MySQL    |  <img src="![pngimg com - mysql_PNG23](https://github.com/user-attachments/assets/1c54de68-f1e2-4a90-bd89-08c03e523901)" alt="Firebase" width="100">    | 10.12.5    |
<br/>

## 5.4 Cooperation
|  |  |
|-----------------|-----------------|
| Git    |  <img src="https://github.com/user-attachments/assets/483abc38-ed4d-487c-b43a-3963b33430e6" alt="git" width="100">    |
| GoogleDocs    |  <img src="![Google-Docs-logo](https://github.com/user-attachments/assets/b086fe5e-c6f1-4fcb-a60f-e9528cc9313b)" alt="Notion" width="100">    |

<br/>

# 6. Project Structure (프로젝트 구조)
```plaintext
project/
├── public/
│   ├── index.html           # HTML 템플릿 파일
│   └── favicon.ico          # 아이콘 파일
├── src/
│   ├── assets/              # 이미지, 폰트 등 정적 파일
│   ├── components/          # 재사용 가능한 UI 컴포넌트
│   ├── hooks/               # 커스텀 훅 모음
│   ├── pages/               # 각 페이지별 컴포넌트
│   ├── App.js               # 메인 애플리케이션 컴포넌트
│   ├── index.js             # 엔트리 포인트 파일
│   ├── index.css            # 전역 css 파일
│   ├── firebaseConfig.js    # firebase 인스턴스 초기화 파일
│   package-lock.json    # 정확한 종속성 버전이 기록된 파일로, 일관된 빌드를 보장
│   package.json         # 프로젝트 종속성 및 스크립트 정의
├── .gitignore               # Git 무시 파일 목록
└── README.md                # 프로젝트 개요 및 사용법
```

<br/>
<br/>

# 7. Development Workflow (개발 워크플로우)
## 브랜치 전략 (Branch Strategy)
우리의 브랜치 전략은 Git Flow를 기반으로 하며, 다음과 같은 브랜치를 사용합니다.

- Main Branch
  - 배포 가능한 상태의 코드를 유지합니다.
  - 모든 배포는 이 브랜치에서 이루어집니다.
  
- {name} Branch
  - 팀원 각자의 개발 브랜치입니다.
  - 모든 기능 개발은 이 브랜치에서 이루어집니다.

<br/>
<br/>
