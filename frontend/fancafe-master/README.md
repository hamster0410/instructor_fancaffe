# KOSTA 2차 프로젝트 : 강사님 팬카페
<br/>

# 📃개요

- 프로젝트 목적
- 아이디어 및 배경
- 팀 멤버 소개
- 작업 및 역할 분담
- 주요 기능
- 사용된 기술 스택
- 프로젝트 구조
- REST API 설계
- ERD 설계
- 동작 화면
- issue 사항
<br/>


# 📌 프로젝트 목적

- 게시판 CRUD를 기반으로한 웹 게시판 구축
- REST API를 기반으로 프레임워크간 통신 구현
<br/>


# 💡 아이디어 및 배경

- 인기 유튜버 **침착맨**의 팬카페인 **침하하**를 모티브로 하여 우리를 가르치느라 수고하시는 강사님에 대한 웹 게시판을 구축하고자 하였습니다.
- 참여형 게시판을 통해 학생들이 서로 코딩 지식을 공유하거나 강사님 명대사나 질문란 등 학생과 강사님간에 소통의 공간을 마련하고자 하였습니다.
<br/>


# 🤼 팀 멤버 소개
| **항목**   | **선우**                                                                                       | **소진**                                                                                       | **정아**                                                                                       |
|:----------|:----------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------|
| **사진**   | ![한선우](https://avatars.githubusercontent.com/u/120350053?v=4)                              | ![최소진](https://github.com/user-attachments/assets/646fbee6-a1b8-402e-9b91-93dd2c31a778)    | ![최정아](https://github.com/user-attachments/assets/20aab45b-c93b-4166-9c77-acb1288f47fe)    |
| **역할**   | BE                                                                                             | FE                                                                                             | FE                                                                                             |
| **GitHub** | [한선우 GitHub](https://github.com/username)                                                   | [최소진 GitHub](https://github.com/username)                                                   | [최정아 GitHub](https://github.com/username)                                                   |
| **주요 작업** | 1. 팀 일정 관리<br>2. 프로젝트 계획 및 관리<br>3. REST API 명세 작성 및 구현<br>4. ERD 설계<br>5. JWT 토큰 인증 구현<br>6. 게시판 CRUD, 좋아요, 댓글 구현 | 1. 회원 관련 기능<br>2. 글 목록 조회 및 수정 삭제<br>3. 이미지 업로드<br>4. 댓글 및 대댓글 로직 구현<br>5. 검색 기능<br>6. 게시글 삭제 및 수정 | 1. 글 편집 에디터<br>2. 글 상세 조회<br>3. 추천/추천취소 기능<br>4. 시간 출력 수정<br>5. 커스텀 훅 생성 및 적용 |
<br/>


# 🔑 주요 기능

- **회원 관리**
    - JWT 토큰 기반 인증 방식을 활용하여 회원가입, 로그인 유지, 회원 전용 기능을 구현했습니다.
- **강사님과의 소통 기능 강화**
    - 조회수 및 좋아요 수를 기반으로 인기글과 전체글을 제공하며, 페이징 처리를 포함한 게시판 CRUD 기능을 구축했습니다.
    - 토스트 에디터를 활용해 이미지 업로드 기능을 지원하고, 카테고리별 게시글 분류 및 게시 가능하도록 설계했습니다.
    - 댓글 및 좋아요 기능을 통해 사용자 간 상호작용을 촉진했습니다.
- **게시글 검색 기능**
    - 카테고리별 검색 기능을 통해 특정 게시글을 빠르고 효율적으로 찾을 수 있도록 구현했습니다.

# <img src="https://github.com/user-attachments/assets/c358e165-b991-4930-85b1-cddc0433a5d9" width="50"> 사용된 기술 스택

![%EC%A0%9C%EB%AA%A9%EC%9D%84-%EC%9E%85%EB%A0%A5%ED%95%B4%EC%A3%BC%EC%84%B8%EC%9A%94_-001](https://github.com/user-attachments/assets/344ce12c-601b-42d2-b48a-53b9440cda87)

<br/>


# 🌌 프로젝트 구조

```agda
Front End (React)

├─assets //스타일링 요소
│  ├─fonts // 폰트
│  ├─img // 이미지
│  └─styles // scss 파일
│      ├─board
│      ├─comment
│      └─layout
│
├─components // 모듈 컴포넌트
│  ├─board //게시판 관련
│  ├─comment //댓글 관련
│  └─layout //화면 레이아웃 관련
│
├─pages // 페이지 컴포넌트
│
└─utils // 커스텀훅, 전역 함수

----------------------------------------------------------------------------------------

Back End (Spring Boot)

project/
├── domain/
│   ├── comment/           # 댓글 도메인 (아래 동일)
│   │      ├── controller           
│   │      ├── dto                  
│   │      ├── entity               
│   │      ├── repository           
│   │      └── service 
│   │
│   ├── heart/             # 좋아요 도메인
│   ├── member/            # 회원 도메인
│   └── post/              # 게시글 도메인
│   
│   
└── global/
    ├── config/              # 보안 및 허용 경로 설정
    ├── controller/          # 이미지 파일 업로드 
    ├── dto/                 # 기본 시간 및 응답 형식
    ├── init/                # 시작 시 초기 디렉토리 설정
    └── security/            # jwt 인증 필터 및 토큰 생성자
```
<br/>


# ✉️ REST API 명세서

<details>
<summary>1. 메인페이지 (인기글)</summary>

- **Method**: GET  
- **URL**: `/`  
- **입력 데이터**: 없음  
- **반환 데이터**: 인기글 목록과 전체 글 수  
    ```json
    {
        "posts": [
            {
                "id": 7,
                "title": "Sample Title 7",
                "hits": 400,
                "nickname": "nick7",
                "category": "Category7",
                "count_comment": 7,
                "count_heart": 5,
                "createdDate": "2024-11-01T16:57:03"
            }
        ],
        "totalCount": 31
    }
    ```

</details>

<details>
<summary>2. 인기글</summary>

- **Method**: GET  
- **URL**: `/best?page=1`  
- **입력 데이터**: 없음  
- **반환 데이터**: 해당 페이지의 인기글 리스트

</details>

<details>
<summary>3. 전체글</summary>

- **Method**: GET  
- **URL**: `/new?page=1`  
- **입력 데이터**: 없음  
- **반환 데이터**: 해당 페이지의 전체글 리스트

</details>

<details>
<summary>4. 카테고리 페이지 리스트</summary>

- **Method**: GET  
- **URL**: `/{category}?page=0`  
- **입력 데이터**: 없음  
- **반환 데이터**: 해당 카테고리의 글 리스트

</details>

<details>
<summary>5. 회원가입</summary>

- **Method**: POST  
- **URL**: `/auth/signup`  
- **입력 데이터**: 
    ```json
    {
        "username": "abc",
        "password": "pw",
        "nickname": "tiny",
        "mail": "tldfk@naver.com"
    }
    ```
- **반환 데이터**: 
    ```json
    {
        "error": null,
        "data": null,
        "message": "SignUp success"
    }
    ```

</details>

<details>
<summary>6. 로그인</summary>

- **Method**: POST  
- **URL**: `/auth/signin`  
- **입력 데이터**:
    ```json
    {
        "username": "abc",
        "password": "pw"
    }
    ```
- **반환 데이터**:
    ```json
    {
        "accessToken": "",
        "refreshToken": ""
    }
    ```

</details>

<details>
<summary>7. 회원정보 변경</summary>

- **Method**: POST  
- **URL**: `/mypage/update`  
- **입력 데이터**:
    ```json
    {
        "nickname": "test",
        "mail": "test@test.com"
    }
    ```
- **반환 데이터**:
    ```json
    {
        "username": "test",
        "nickname": "test"
    }
    ```

</details>

<details>
<summary>8. 회원 탈퇴</summary>

- **Method**: DELETE  
- **URL**: `/auth/delete`  
- **입력 데이터**: 토큰 삭제  
- **반환 데이터**:
    ```json
    {
        "error": null,
        "message": "delete success",
        "data": null
    }
    ```

</details>

<details>
<summary>9. 액세스 토큰 재발급</summary>

- **Method**: POST  
- **URL**: `/auth/token`  
- **입력 데이터**:
    ```json
    {
        "accessToken": "",
        "refreshToken": ""
    }
    ```
- **반환 데이터**:
    ```json
    {
        "accessToken": "",
        "refreshToken": ""
    }
    ```

</details>

<details>
<summary>10. 글 작성</summary>

- **Method**: POST  
- **URL**: `/post/write`  
- **입력 데이터**:
    ```json
    {
        "title": "test",
        "contents": "contents",
        "category": "categoryA"
    }
    ```

</details>

<details>
<summary>11. 글 조회</summary>

- **Method**: GET  
- **URL**: `/category/post_id?group=humor&page=1`  
- **입력 데이터**: 없음  
- **반환 데이터**: 해당 글 정보 및 조회수 증가

</details>

<details>
<summary>12. 작성자 확인</summary>

- **Method**: GET  
- **URL**: `/category/postid/edit`  
- **입력 데이터**: 없음  
- **반환 데이터**: 작성자 정보 확인 (Frontend에서 수정 제어)

</details>

<details>
<summary>13. 글 수정</summary>

- **Method**: PUT  
- **URL**: `/category/postid/edit`  
- **입력 데이터**:
    ```json
    {
        "title": "test",
        "contents": "contents",
        "category": "categoryA"
    }
    ```

</details>

<details>
<summary>14. 글 삭제</summary>

- **Method**: DELETE  
- **URL**: `/category/postid/delete`  
- **입력 데이터**: 없음  
- **반환 데이터**:
    ```json
    {
        "error": null,
        "data": null,
        "message": "delete success"
    }
    ```

</details>

<details>
<summary>15. 글 검색</summary>

- **Method**: GET  
- **URL**: `/search/about?keyword&page=1`  
- **입력 데이터**: `keyword` (필수)  
- **반환 데이터**:
    ```json
    {
        "posts": [
            {
                "id": 62,
                "title": "heart",
                "hits": 27,
                "nickname": "tamer",
                "category": "Category1",
                "count_comment": 0,
                "count_heart": 0,
                "createdDate": "2024-11-06T09:53:12.2257"
            }
        ],
        "totalCount": 1
    }
    ```

</details>

<details>
<summary>16. 게시글 추천</summary>

- **Method**: GET  
- **URL**: `/heart/add/postId`  
- **입력 데이터**: 없음  
- **반환 데이터**:
    ```json
    {
        "error": null,
        "data": null,
        "message": "heart success"
    }
    ```

</details>

<details>
<summary>17. 게시글 추천 취소</summary>

- **Method**: GET  
- **URL**: `/heart/delete/postId`  
- **입력 데이터**: 없음  
- **반환 데이터**:
    ```json
    {
        "error": null,
        "data": null,
        "message": "delete success"
    }
    ```

</details>

<details>
<summary>18. 댓글 리스트 출력</summary>

- **Method**: GET  
- **URL**: `/comment/postId?page=1`  
- **입력 데이터**: 없음  
- **반환 데이터**: 댓글 목록 (페이징 처리)

</details>

<details>
<summary>19. 댓글 생성</summary>

- **Method**: POST  
- **URL**: `/comment/create/postId`  
- **입력 데이터**:
    ```json
    {
        "parentId": "testParent",
        "content": "testcontent"
    }
    ```
- **반환 데이터**:
    ```json
    {
        "error": null,
        "data": null,
        "message": "comment success"
    }
    ```

</details>

<details>
<summary>20. 댓글 작성자 확인</summary>

- **Method**: GET  
- **URL**: `/comment/update/commentId`  
- **입력 데이터**: 없음  
- **반환 데이터**: 댓글 작성자 여부 확인 (true/false)

</details>

<details>
<summary>21. 댓글 수정</summary>

- **Method**: PUT  
- **URL**: `/comment/update/commentId`  
- **입력 데이터**:
    ```json
    {
        "content": "i am contents"
    }
    ```

</details>

<details>
<summary>22. 댓글 삭제</summary>

- **Method**: DELETE  
- **URL**: `/comment/delete/commentId`  
- **입력 데이터**: 없음  
- **반환 데이터**:
    ```json
    {
        "error": null,
        "data": null,
        "message": "delete success"
    }
    ```

</details>

<details>
<summary>23. 파일 이미지</summary>

- **Method**: GET  
- **URL**: `/file/comment?filename=abc.png`  
- **입력 데이터**: 파일명 (`filename`)  
- **반환 데이터**: 해당 파일 이미지

</details>

<details>
<summary>24. 파일 이미지 저장</summary>

- **Method**: POST  
- **URL**: `/file/image-upload`  
- **입력 데이터**: 이미지 파일 (`image`)

</details>

<br/>



# 🧱  ERD 설계

![image](https://github.com/user-attachments/assets/21368172-5e77-4df5-890b-b0e89556abee)
<br/>


# 🔥 동작 화면
<br/>


# ❓ Issue 사항
