# 🍀️📦 LuckyVickyLogistics🍀🍀
<img width="600" height="400" alt="image" src="https://github.com/user-attachments/assets/3d1e90e0-cef1-4ebb-ba78-d675662ddfc0" />
<br>

## ❇️ [프로젝트 개요](https://github.com/LuckyVickyLogistics/backend-v1/tree/develop?tab=readme-ov-file)
#### B2B 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼
<details>
<summary>[ERD](https://www.erdcloud.com/d/eFc8S6qtouLNEfYKF)</summary>
- ERD 이미지 삽입(여기넣어주세용)
</details>
- [API 명세서](https://teamsparta.notion.site/29f2dc3ef51480528907cccc534ae546?v=29f2dc3ef51480bcaad3000c10b8edbd)

## 서비스 구성 및 실행방법!
```
프로젝트 파일에서 
cd infra
docker compose up -d 실행 (db 및 docker 세팅 완료)
eureka-server 먼저 실행 후 다른 서비스 실행
```
 

## 👨‍👩‍👧‍👦 팀원 소개
| 이름                          | 프로필 | 담당 역할                                                                                                                                                |
|-----------------------------| --- |------------------------------------------------------------------------------------------------------------------------------------------------------|
| [김부경](https://github.com/)  | <img src="https://via.placeholder.com/150" width="120" />  | `배송`<br>배송 CRUD 기능 구현 <br>`배송담당자`<br>배송담당자 CRUD 기능 구현<br> `배송경로`<br>배송경로 조회 및 변경, 배송경로 알고리즘 개발<br>  `공통`<br>UserRole Enum 정의<br>에러코드 정의 및 예외 처리 핸들러<br> |
| [김진현](https://github.com/)  | <img src="https://github.com/user-attachments/assets/96c5ca6f-c366-49d6-9756-7c0939cbed92" width="120" /> | `상품`<br>상품 CRUD 기능 구현<br>상품 상태 실시간 확인 및 재고 변경 로직 개발<br>`주문`<br>주문 RUD 개발<br>`공통`<br>각 서비스 권한별 분기 처리                                                  |
| [김채연](https://github.com/)  | <img src="https://via.placeholder.com/150" width="120" />  | `업체`<br>업체 CRUD 기능 구현<br> `Gateway`<br>gateWay 세팅<br> `공통` <br>프로젝트의 전반 공통 기능들을 common 으로 마이그레이션 하며 기타 오류 처리<br>                                     |
| [이건희](https://github.com/)  | <img src="https://via.placeholder.com/150" width="120" />  | `Slack`<br>메세지 발송,목록 조회, 상태 수정, 삭제 구현<br> `AI`<br>Gemini를 사용해 프롬프트 CRUD 기능 구현 <br>`주문`<br>주문 C 개발 + Kafka 처리<br>                                     |
| [이예나](https://github.com/)  | <img src="https://via.placeholder.com/150" width="120" /> | `사용자`<br>회원가입 및 로그인 처리, 회원 관리 기능 개발 <br>`인증/인가`<br>JWT 인증 구현<br>                                                                                     |
| [홍태휘](https://github.com/)  | <img src="https://via.placeholder.com/150" width="120" />  | `허브`<br>허브 CRUD 기능 개발 및 허브의 위도,경도를 포함한 데이터 기본 세팅<br>`배송경로`<br>배송경로 알고리즘 개발</br>                                                                      |
## 🚀 기술 스택

Category | Stack
--- | --- |
Language | ![Java](https://img.shields.io/badge/java%2017-007396?style=for-the-badge&logo=java&logoColor=white)
IDE | ![intellij-idea](https://img.shields.io/badge/intellij%20idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white) 
Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot%203.5.7-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
Build Tool | ![gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
Database | ![PostgreSQL](https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
Library | ![Spring Security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) ![JPA](https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge) ![Spring Cloud](https://img.shields.io/badge/spring%20cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![Spring Cloud Gateway](https://img.shields.io/badge/spring%20cloud%20gateway-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![Eureka](https://img.shields.io/badge/eureka-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
API | ![Swagger](https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) ![Google Gemini](https://img.shields.io/badge/google%20gemini-8E75B2?style=for-the-badge&logo=googlegemini&logoColor=white) ![RestClient](https://img.shields.io/badge/RestClient-007396?style=for-the-badge)
DevOps | ![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) ![Redis](https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white) ![Kafka](https://img.shields.io/badge/apache%20kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
Tools | ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white) ![git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white) ![slack](https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white) ![notion](https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white) ![discord](https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)

<details>
<summary><strong>📣기술 & 라이브러리 선정 이유</strong></summary>
<div markdown="1">   
  <br/>
  <details>
  <summary><strong> 1️⃣ Spring Boot 3.5.7</strong></summary>
    <div markdown="1"> 

    1. 자동 설정 기능으로 개발 생산성이 높으며 설정 부담이 적다는 장점이 있습니다.
    2. 스타터 패키지 제공으로 의존성 관리가 간편합니다.
    3. 내장 Tomcat 서버를 제공하여 별도의 WAS 설정 없이 바로 실행 가능합니다.
    4. 최신 버전으로 보안 패치와 성능 개선이 반영되어 있습니다.
    5. 3.x 버전은 현대적 아키텍쳐와의 호환성이 높다는 장점이 있습니다.

  </details> 

  <details>
  <summary><strong> 2️⃣ PostgreSQL</strong></summary>
    <div markdown="1">     

    1. 무료로 제공되는 오픈소스 RDBMS입니다.
    2. 안정성과 확장성이 뛰어나며 대규모 환경에서도 안정적으로 동작합니다.
    3. JSON 타입 지원 등 다양한 데이터 타입을 제공합니다.
    4. ACID 특성을 완벽하게 지원하여 데이터 무결성을 보장합니다.
    5. 대규모 트랜잭션 처리 및 복잡한 쿼리 처리에 강하다는 장점이 있습니다.

  </details> 

  <details>
  <summary><strong> 3️⃣ JWT & Spring Security</strong></summary>
    <div markdown="1">     

    1. 서버 상태를 저장하지 않는 Stateless한 인증 방식으로 확장성이 좋습니다.
    2. Spring Security와의 완벽한 통합으로 보안 구현이 용이합니다.
    3. 토큰 기반 인증으로 세션 관리 부담이 없습니다. 즉, 서버의 부담이 적습니다.
    4. 역할 기반 접근 제어(RBAC)를 쉽게 구현할 수 있습니다.
    5. MSA 구조에서 서비스 간의 인증과 인가 적용에 유리합니다.

  </details> 

  <details>
  <summary><strong> 4️⃣ Docker</strong></summary>
    <div markdown="1">

    1. Docker파일을 기반으로 팀원 모두가 동일한 개발 환경을 쉽게 구성할 수 있으며,
       환경 차이로 인한 오류를 방지 할 수 있습니다. (개발 환경과 운영 환경의 일관성 유지)
    2. 각 MSA 서비스는 독립된 컨테이너에서 실행되어 시스템 간의 간섭 없이 안정적으로 테스트 가능합니다.
    3. 이미지 기반의 배포 방식이기 때문에 일관되고 빠른 배포가 가능합니다.
    4. 마이크로서비스 아키텍처로의 확장이 용이합니다.
     -> 서비스 단위로 배포, 스케일링이 용이합니다.
    5. 추후 CI/CD 파이프라인과 연동해 자동화된 배포 환경을 구축 가능합니다.

  </details> 

  <details>
  <summary><strong> 5️⃣ Redis </strong></summary>
    <div markdown="1">     

    1. Redis는 인메모리 기반으로 매우 빠른 읽기 및 쓰기 성능을 제공합니다
    2. MSA 환경에서 트래픽 분산 및 응답 속도 개선, lock을 통한 동시성 제어가 가능합니다.
    3. TTL 기능으로 데이터 만료 처리가 간편합니다.

  </details> 

  <details>
  <summary><strong> 6️⃣ RestClient</strong></summary>
    <div markdown="1">     

    1. RestTemplate의 후속 버전으로 더 나은 성능과 유지보수성을 제공합니다.
    2. 동기 방식으로 구현이 간단하고 직관적입니다.
    3. 예외 처리가 명확해 안정적인 API 호출을 구성할 수 있습니다.
    4. 현재 프로젝트 규모에서는 비동기보다 동기 방식이 더 적합합니다.
    5. 외부 API나 MSA 간 서비스 호출 시 코드의 가독성이 더 좋습니다.

  </details> 

  <details>
  <summary><strong> 7️⃣ Google Gemini API</strong></summary>
    <div markdown="1">

    1. Java/Spring 환경에서도 손쉽게 연동할 수 있습니다
    2. 자연스러운 한국어 생성이 가능합니다.
    3. API 호출이 간단하여 빠르게 통합할 수 있습니다.
    4. 무료 티어로 프로젝트 테스트가 가능합니다.
    5. 텍스트 요약, 분류, 문장 생성 등 다양한 기능을 활용할 수 있습니다.

  </details> 

  <details>
  <summary><strong> 8️⃣ eureka + gateway</strong></summary>  
  <div markdown="1">     

    1. Eureka는 서비스 관련 기능을 제공해 MSA 환경에서 서비스 위치를 자동으로 관리할 수 있습니다.
    2. Eureka Server를 먼저 실행해 각 마이크로서비스(Product, Order, Company, Hub 등)가 자동으로 등록되도록 구성했습니다.
    3. 서비스들이 Eureka에 등록되면 Gateway가 해당 정보를 기반으로 자동 라우팅을 수행하기 때문에, 별도의 수동 설정 없이 서비스 추가·변경을 처리할 수 있습니다.
    4. 서비스 간 결합도를 낮추고, 각각의 서비스가 독립적으로 배포·확장될 수 있어 MSA 환경에서 유연한 확장성을 확보할 수 있습니다.

  </details> 

  <details>
  <summary><strong> 9️⃣ Kafka </strong></summary>  
  <div markdown="1">     

    1.실시간 스트림 처리 구조를 구성하여 재고 동기화, 주문 상태 업데이트, 알림 기능 등을 효율적으로 구현할 수 있습니다.
    2.Kafka는 대용량 실시간 데이터 처리에 강해 주문 생성, 재고 변경, 배송 상태 변경 등 이벤트 기반 처리에 적합합니다.
    3.MSA 환경에서 서비스 간 직접적인 의존성을 줄이고, 비동기 이벤트 기반 통신을 가능하게 하여 시스템 결합도를 낮춥니다.

  </details> 

</div>
</details>
</br>

## 📁 아키텍처
<p align="center">
  <img src="https://github.com/user-attachments/assets/9bd325e8-17b6-488a-9184-016444e5c9a1" width="500" />
</p>

```
럭키비키로지스틱스 아키텍처는 다음과 같이 구성됩니다:
- **Microservices Architecture(MSA)** 기반 설계
- **Aggregate** 단위로 각 서비스 분리
- Eureka Sever에 각 서비스를 등록하고 **Gateway**를 통해 서버 간 통신
-  Docker 컨테이너화로 일관된 실행 환경 제공
```

<br>


### 테이블 구조 (총 13개)
<details>
<summary><strong>서비스별 상세 테이블 구조</strong></summary>

#### 👤 User (1개)
- `p_user`

  | 컬럼명              | 데이터 타입        | 제약 조건                  |
    |--------------------|-----------------|---------------------------|
  | user_id            | bigint          | PK, NOT NULL              |
  | identifier         | uuid            | UNIQUE, NOT NULL          |
  | organization_type  | varchar(255)    |                           |
  | password           | varchar(255)    | NOT NULL                  |
  | role               | varchar(255)    | NOT NULL                  |
  | slack_id           | varchar(255)    | NOT NULL                  |
  | status             | varchar(255)    |                           |
  | username           | varchar(100)    | UNIQUE, NOT NULL          |
  | created_at         | timestamp(6)    |                           |
  | created_by         | varchar(100)    |                           |
  | updated_at         | timestamp(6)    |                           |
  | updated_by         | varchar(100)    |                           |
  | deleted_at         | timestamp(6)    |                           |
  | deleted_by         | varchar(100)    |                           |


#### 🏪 Hub (3개)
- `p_hub`

  | 컬럼명              | 데이터 타입        | 제약 조건                  |
    |--------------------|-----------------|---------------------------|
  | user_id            | bigint          | PK, NOT NULL              |
  | identifier         | uuid            | UNIQUE, NOT NULL          |
  | organization_type  | varchar(255)    |                           |
  | password           | varchar(255)    | NOT NULL                  |
  | role               | varchar(255)    | NOT NULL                  |
  | slack_id           | varchar(255)    | NOT NULL                  |
  | status             | varchar(255)    |                           |
  | username           | varchar(100)    | UNIQUE, NOT NULL          |
  | created_at         | timestamp(6)    |                           |
  | created_by         | varchar(100)    |                           |
  | updated_at         | timestamp(6)    |                           |
  | updated_by         | varchar(100)    |                           |
  | deleted_at         | timestamp(6)    |                           |
  | deleted_by         | varchar(100)    |                           |

- `p_hub_manager`

  | 컬럼명          | 데이터 타입     | 제약 조건        |
    |----------------|----------------|----------------|
  | hub_manager_id  | uuid           | PK, NOT NULL   |
  | user_id         | bigint         | NOT NULL       |
  | hub_id          | uuid           | NOT NULL       |
  | is_deleted      | boolean        |                |
  | name            | varchar(255)   |                |
  | slack_id        | varchar(255)   |                |
  | created_at      | timestamp      | NOT NULL       |
  | created_by      | bigint         |                |
  | updated_at      | timestamp      |                |
  | updated_by      | bigint         |                |
  | deleted_at      | timestamp      |                |
  | deleted_by      | bigint         |                |

- `p_hub_route`

  | 컬럼명       | 데이터 타입       | 제약 조건        |
    |-------------|-----------------|----------------|
  | route_id    | uuid            | PK, NOT NULL   |
  | distance    | doubleprecision | NOT NULL       |
  | time        | integer         | NOT NULL       |
  | from_hub_id | uuid            | NOT NULL       |
  | to_hub_id   | uuid            | NOT NULL       |
  | is_deleted  | boolean         |                |
  | created_at  | timestamp       | NOT NULL       |
  | created_by  | bigint          |                |
  | updated_at  | timestamp       |                |
  | updated_by  | bigint          |                |
  | deleted_at  | timestamp       |                |
  | deleted_by  | bigint          |                |

#### 🛒 Product (1개)
- `p_products`

  | 컬럼명                 | 데이터 타입     | 제약 조건        |
    |-----------------------|----------------|----------------|
  | product_id            | uuid           | PK, NOT NULL   |
  | company_id            | uuid           | NOT NULL       |
  | hub_id                | uuid           | NOT NULL       |
  | price                 | integer        | NOT NULL       |
  | product_name          | varchar(255)   | NOT NULL       |
  | product_quantity      | integer        | NOT NULL       |
  | p_status              | varchar(255)   | NOT NULL       |
  | product_total_quantity| integer        | NOT NULL       |
  | created_by            | varchar(255)   |                |
  | updated_by            | varchar(255)   |                |
  | deleted_by            | varchar(255)   |                |
  | created_at            | timestamp      | NOT NULL       |
  | updated_at            | timestamp      |                |
  | deleted_at            | timestamp      |                |


#### 🎁 Order (1개)
- `p_orders`

  | 컬럼명            | 데이터 타입     | 제약 조건        |
    |------------------|----------------|----------------|
  | order_id         | uuid           | PK, NOT NULL   |
  | quantity         | integer        | NOT NULL       |
  | customer_id      | uuid           | NOT NULL       |
  | delivery_id      | uuid           |                |
  | product_id       | uuid           | NOT NULL       |
  | supplier_id      | uuid           | NOT NULL       |
  | delivery_address | varchar(255)   | NOT NULL       |
  | request          | varchar(255)   | NOT NULL       |
  | status           | varchar(255)   | NOT NULL       |
  | created_by       | varchar(255)   |                |
  | updated_by       | varchar(255)   |                |
  | deleted_by       | varchar(255)   |                |
  | created_at       | timestamp      | NOT NULL       |
  | updated_at       | timestamp      |                |

#### 📦 Delivery (3개)
- `p_delivery`

  | 컬럼명                     | 데이터 타입     | 제약 조건        |
    |----------------------------|----------------|----------------|
  | delivery_id                | uuid           | PK, NOT NULL   |
  | arrival_hub_id             | uuid           | NOT NULL       |
  | delivery_address           | varchar(500)   | NOT NULL       |
  | departure_hub_id           | uuid           | NOT NULL       |
  | order_id                   | uuid           | NOT NULL       |
  | recipient_name             | varchar(100)   | NOT NULL       |
  | recipient_slack_id         | varchar(100)   | NOT NULL       |
  | status                     | varchar(20)    | NOT NULL       |
  | company_delivery_manager_id| bigint         | NOT NULL       |
  | created_at                 | timestamp      | NOT NULL       |
  | created_by                 | bigint         |                |
  | updated_at                 | timestamp      |                |
  | updated_by                 | bigint         |                |
  | deleted_at                 | timestamp      |                |
  | deleted_by                 | bigint         |                |

- `p_delivery_manager`

  | 컬럼명              | 데이터 타입     | 제약 조건        |
    |--------------------|----------------|----------------|
  | delivery_manager_id| bigint         | PK, NOT NULL   |
  | delivery_sequence  | integer        | NOT NULL       |
  | hub_id             | varchar(255)   |                |
  | slack_id           | varchar(100)   | NOT NULL       |
  | type               | varchar(20)    | NOT NULL       |
  | end_time           | time           | NOT NULL       |
  | start_time         | time           | NOT NULL       |
  | created_at         | timestamp      | NOT NULL       |
  | created_by         | bigint         |                |
  | updated_at         | timestamp      |                |
  | updated_by         | bigint         |                |
  | deleted_at         | timestamp      |                |
  | deleted_by         | bigint         |                |

- `p_delivery_route`

  | 컬럼명               | 데이터 타입      | 제약 조건        |
    |---------------------|-----------------|----------------|
  | delivery_route_id   | uuid            | PK, NOT NULL   |
  | actual_distance     | numeric(10,2)   |                |
  | actual_duration     | integer         |                |
  | arrival_hub_id      | uuid            | NOT NULL       |
  | delivery_id         | uuid            | NOT NULL       |
  | departure_hub_id    | uuid            | NOT NULL       |
  | estimated_distance  | numeric(10,2)   | NOT NULL       |
  | estimated_duration  | integer         | NOT NULL       |
  | sequence            | integer         | NOT NULL       |
  | status              | varchar(20)     | NOT NULL       |
  | hub_delivery_manager_id | bigint      | NOT NULL       |
  | created_at          | timestamp       | NOT NULL       |
  | created_by          | bigint          |                |
  | updated_at          | timestamp       |                |
  | updated_by          | bigint          |                |
  | deleted_at          | timestamp       |                |
  | deleted_by          | bigint          |                |

#### 🕊 Company (2개)
- ``
- ``

#### 🤖 AI (1개)
- `p_ai_prompt`

  | 컬럼명            | 데이터 타입  | 제약 조건      |
    |------------------|--------------|----------------|
  | ai_prompt_id     | uuid         | PK, NOT NULL   |
  | request_content  | text         | NOT NULL       |
  | status           | varchar(255) | NOT NULL       |
  | created_at       | timestamp    |                |
  | created_by       | bigint       |                |
  | updated_at       | timestamp    |                |
  | updated_by       | bigint       |                |
  | deleted_at       | timestamp    |                |
  | deleted_by       | bigint       |                |
  | response_content | timestamp    |                |

#### 🕊 SLACK (1개)
- `p_slack_message`

  | 컬럼명          | 데이터 타입  | 제약 조건      |
    |----------------|--------------|----------------|
  | slack_message_id | uuid         | PK, NOT NULL   |
  | content          | text         | NOT NULL       |
  | receiver_email   | varchar(255) | NOT NULL       |
  | status           | varchar(255) | NOT NULL       |
  | created_at       | timestamp    |                |
  | created_by       | bigint       |                |
  | updated_at       | timestamp    |                |
  | updated_by       | bigint       |                |
  | deleted_at       | timestamp    |                |
  | deleted_by       | bigint       |                |


</details>

<br>


### 도메인 구성
```
✅ Gateway : 헤더의 토큰 파싱 후 인가 처리
✅ User: 회원 관리, JWT 기반 액세스 토큰, 리프레시 토큰을 통한 인증 처리
✅ Hub: 허브 관리, 각 허브 간 경로 관리, 허브 관리자 관리
✅ Product: 상품 관리
✅ Order: 주문 관리, 배송 생성 요청
✅ Delivery : 배송 관리, 배송 경로 관리, 배송 담당자 관리
✅ Company : 업체 관리
✅ AI: Google Gemini API를 활용한 최종 발송 시한 계산
✅ Slack: Slack API를 활용한 개별 다이렉트 메시지 발송
```

<br>

##  🛠 주요 기능
```
👨‍👩‍👧 유저: 로그인 | 회원가입 | JWT 인증 | 권한 관리
🏪 허브: 
🍱 업체: 
🛒 상품: 
🎁 주문: 주문 생성 | 상태 관리 | 주문 내역 조회
📦 배송: 
💳 AI : 
⭐ SLACK: 

```

<details>
  <summary><strong>1️⃣ 사용자 및 JWT 인증/인가</strong></summary>
  <br>

- [x] Spring Security와 JWT를 활용한 Stateless 인증
- [x] 토큰 기반 인증으로 확장성 확보
- [x] 로그인 및 기본 회원가입 기능 제공
</details>

<details>
  <summary><strong>2️⃣ 허브</strong></summary>
  <br>

- [x] 
- [x] 
- [x] 
- [x] 
</details>

<details>
  <summary><strong>3️⃣ 업체 </strong></summary>
  <br>

- [x] 
- [x] 
- [x] 
- [x] 
</details>

<details>
  <summary><strong>4️⃣ 상품</strong></summary>
  <br>

- [x] 검색 기능
- [x] 
- [x] 
- [x]
</details>

<details>
  <summary><strong>5️⃣ 주문 </strong></summary>
  <br>

- [x] 주문 생성 및 상태 관리
- [x] 
- [x] 
- [x] 
</details>

<details>
  <summary><strong>6️⃣ 배송 </strong></summary>
  <br>

- [x] 
- [x]
- [x] 
- [x] 기본 배송지 설정
</details>

<details>
  <summary><strong>7️⃣ AI</strong></summary>
  <br>

- [x] Google Gemini API 연동
- [x] AI 호출 로그 저장
- [x] 
</details>

<details>
  <summary><strong> 8️⃣ Slack</strong></summary>
  <br>

- [x] 
- [x] 
- [x] 
</details>

<br>



## 🐞 Trouble Shooting (수정 필요)

<details>
  <summary><strong>1️⃣ PostgreSQL 설치 오류 해결</strong></summary>
    <div markdown="1"> 

**문제**
- PostgreSQL을 로컬에 설치하는 과정에서 에러 코드 1 발생
- 설치 경로에 한글이 포함되어 설치 실패

**원인**
- 한글 경로로 인한 설치 오류

**해결 방안**
- DBeaver를 사용하여 원격 RDS에 직접 접근
- 로컬 설치 없이 개발 환경 구성

**결과**
- ✅ 팀원 모두 동일한 DB 환경에서 작업 가능
- ✅ 초기 환경 구축 시간 단축
- ✅ 경로 오류 근본적 해결

</details>

<details>
  <summary><strong>2️⃣ Spring Security 권한 거부 응답 개선</strong></summary>
    <div markdown="1"> 

**문제**
- `@PreAuthorize`로 권한 제어 시 403 에러만 반환
- Swagger에서 에러 상세 정보 확인 불가

**원인**
- 기본 `AccessDeniedHandler`가 HTML 기반 에러 페이지 반환
- API 클라이언트에서는 단순 403만 표시됨

**해결 방안**
- 커스텀 예외 처리 핸들러 구현
- JSON 형태의 상세 에러 응답 반환

**결과**
```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "접근 권한이 없습니다.",
  "path": "/api/v1/user"
}
```
- ✅ 명확한 에러 메시지 제공
- ✅ 디버깅 효율 향상
- ✅ API 응답 일관성 확보

</details>

<details>
  <summary><strong>3️⃣ 주문 생성 시 다중 오류 해결</strong></summary>
    <div markdown="1"> 

**문제**
- POST `/api/orders` 호출 시 순차적 오류 발생
    1. `HttpMediaTypeNotAcceptableException`
    2. `HttpMessageNotReadableException`
    3. SQL NOT NULL 제약 조건 위반

**원인**
1. DTO에 getter 없어 JSON 변환 불가
2. `@RequestBody String` 타입 불일치
3. 클래스 레벨 `@Transactional(readOnly = true)`로 INSERT 무시
4. 엔티티와 DB 스키마 불일치

**해결 방안**
1. DTO를 `record`로 변경하여 자동 getter 생성
2. 요청 DTO 타입을 적절한 객체로 변경
3. 쓰기 메서드에 `@Transactional` 오버라이드
4. DB 스키마와 엔티티 동기화

**결과**
- ✅ JSON 직렬화/역직렬화 정상 작동
- ✅ 트랜잭션 정상 커밋
- ✅ 불필요한 컬럼 제거
- ✅ 안정적인 주문 생성 기능

</details>

<details>
  <summary><strong>4️⃣ 회원가입 시 인증 정보 오류</strong></summary>
    <div markdown="1"> 

**문제**
- 회원가입 시 `UserDetailsImpl` 캐스팅 오류 발생
- SecurityContextHolder가 인증정보를 찾지 못함

**원인**
- 회원가입은 인증 없이 접근 가능한 API
- principal이 "anonymous" 문자열이라 캐스팅 불가

**해결 방안**
```java
if (principal instanceof UserDetailsImpl) {
    return ((UserDetailsImpl) principal).getUsername();
}
return "SYSTEM";
```

**결과**
- ✅ 미인증 사용자 작업 시 "SYSTEM" 기록
- ✅ 인증된 사용자는 닉네임 기록
- ✅ created_by, updated_by 추적 가능

</details>

<details>
  <summary><strong>5️⃣ Soft Delete 데이터 조회 문제</strong></summary>
    <div markdown="1"> 

**문제**
- Cart 조회 시 삭제된 CartFood와 CartFoodOption까지 조회됨
- Soft Delete한 데이터가 함께 반환됨

**원인**
- 연관 엔티티에 조회 조건이 적용되지 않음
- `is_deleted = true`인 데이터까지 전부 조회

**해결 방안**
```java
@OneToMany(mappedBy = "cart")
@SQLRestriction("is_deleted = false")
private List<CartFood> cartFoods;
```

**결과**
- ✅ 삭제되지 않은 데이터만 조회
- ✅ 데이터 정합성 유지
- ✅ 불필요한 필터링 로직 제거

</details>

<details>
  <summary><strong>6️⃣ 장바구니 음식 추가 시 ID 미생성 오류</strong></summary>
    <div markdown="1"> 

**문제**
- 새 장바구니 생성 후 음식 추가 시 오류 발생
- 외래키 참조 실패

**원인**
- `Cart.create()`로 생성한 엔티티를 저장하지 않고 사용
- ID가 생성되지 않은 상태에서 참조 시도

**해결 방안**
```java
Cart newCart = Cart.create(user, restaurant);
cartRepository.save(newCart); // 저장 후 사용
```

**결과**
- ✅ 안전한 데이터 저장 및 조회
- ✅ 외래키 참조 정상 작동
- ✅ 장바구니 기능 안정화

</details>

<details>
  <summary><strong>7️⃣ 식당 정보 접근 권한 문제</strong></summary>
    <div markdown="1"> 

**문제**
- 식당 등록/수정/삭제 시 본인 확인 없이 접근 가능
- 다른 사장님의 식당 정보 수정 가능

**원인**
- 본인 ID 체크 로직 부재
- 권한만 확인하고 소유권은 미확인

**해결 방안**
```java
if (!restaurant.getOwnerId().equals(user.getId())) {
    throw new UnauthorizedException("본인의 식당만 수정 가능합니다");
}
```

**결과**
- ✅ 본인 식당만 관리 가능
- ✅ 권한과 소유권 이중 검증
- ✅ 데이터 보안 강화

</details>

<details>
  <summary><strong>8️⃣ 리뷰 재작성 시 Unique 제약 조건 위반</strong></summary>
    <div markdown="1"> 

**문제**
- 리뷰 작성 → 삭제 → 재작성 시 오류 발생
- Soft Delete로 인해 실제 데이터는 남아있어 Unique 제약 위반

**원인**
- 주문과 리뷰가 1:1 관계
- 외래키에 Unique 제약 조건 존재

**해결 방안**
1. 주문과 리뷰 관계를 1:N으로 변경
2. 리뷰 생성 시 활성 상태 확인 로직 추가
```java
if (reviewRepository.existsByOrderIdAndStatusTrue(orderId))