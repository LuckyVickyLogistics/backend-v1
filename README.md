# 🍀️📦 LuckyVickyLogistics🍀🍀
<img width="600" height="400" alt="image" src="https://github.com/user-attachments/assets/3d1e90e0-cef1-4ebb-ba78-d675662ddfc0" />
<br>

## ❇️ [프로젝트 개요](https://github.com/LuckyVickyLogistics/backend-v1/tree/develop?tab=readme-ov-file)
#### B2B 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼
<details>
<summary>[ERD](https://www.erdcloud.com/d/eFc8S6qtouLNEfYKF)</summary>
<img width="700" height="600" alt="image" src="https://github.com/user-attachments/assets/4321d706-ef7b-4185-a933-5e2d60bc260f" />

</details>
[API 명세서](https://teamsparta.notion.site/29f2dc3ef51480528907cccc534ae546?v=29f2dc3ef51480bcaad3000c10b8edbd)

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
| [김부경](https://github.com/)  | <img src="https://github.com/user-attachments/assets/b8bd0eaf-8f5d-4024-9848-2a53ede5e331" width="120" />  | `배송`<br>배송 CRUD 기능 구현 <br>`배송담당자`<br>배송담당자 CRUD 기능 구현<br> `배송경로`<br>배송경로 조회 및 변경, 배송경로 알고리즘 개발<br>  `공통`<br>UserRole Enum 정의<br>에러코드 정의 및 예외 처리 핸들러<br> |
| [김진현](https://github.com/)  | <img src="https://github.com/user-attachments/assets/96c5ca6f-c366-49d6-9756-7c0939cbed92" width="120" /> | `상품`<br>상품 CRUD 기능 구현<br>상품 상태 실시간 확인 및 재고 변경 로직 개발<br>`주문`<br>주문 RUD 개발<br>`공통`<br>각 서비스 권한별 분기 처리                                                  |
| [김채연](https://github.com/)  | <img src="https://github.com/user-attachments/assets/b4584ff5-1479-44c4-8f6d-d3e94e9aba44" width="120" />  | `업체`<br>업체 CRUD 기능 구현<br> `Gateway`<br>gateWay 세팅<br> `공통` <br>프로젝트의 전반 공통 기능들을 common 으로 마이그레이션 하며 기타 오류 처리<br>                                     |
| [이건희](https://github.com/)  | <img src="https://github.com/user-attachments/assets/b97a3423-1711-4175-a44e-55d74128ce69" width="120" />  | `Slack`<br>메세지 발송,목록 조회, 상태 수정, 삭제 구현<br> `AI`<br>Gemini를 사용해 프롬프트 CRUD 기능 구현 <br>`주문`<br>주문 C 개발 + Kafka 처리<br>                                     |
| [이예나](https://github.com/)  | <img src="https://github.com/user-attachments/assets/26eefb33-ed31-44d1-97ba-37e58311f492" width="120" /> | `사용자`<br>회원가입 및 로그인 처리, 회원 관리 기능 개발 <br>`인증/인가`<br>JWT 인증 구현<br>                                                                                     |
| [홍태휘](https://github.com/)  | <img src="https://github.com/user-attachments/assets/f932bc07-3d66-4220-a462-4cbefd816fd4" width="120" />  | `허브`<br>허브 CRUD 기능 개발 및 허브의 위도,경도를 포함한 데이터 기본 세팅<br>`배송경로`<br>배송경로 알고리즘 개발</br>                                                                      |
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
- `p_company`

  | 컬럼명       | 데이터 타입 | 제약 조건        |
      |-------------|------------|----------------|
  | company_id  | uuid       | PK, NOT NULL   |
  | hub_id      | uuid       | NOT NULL       |
  | name        | varchar    | NOT NULL       |
  | address     | varchar    | NOT NULL       |
  | type        | varchar    | NOT NULL       |
  | created_at  | timestamp  | NOT NULL       |
  | created_by  | bigint     | NOT NULL       |
  | updated_at  | timestamp  |                |
  | updated_by  | bigint     |                |
  | deleted_at  | timestamp  |                |
  | deleted_by  | bigint     |                |

- `p_company_manager`

  | 컬럼명      | 데이터 타입 | 제약 조건        |
      |------------|------------|----------------|
  | manager_id | uuid       | PK, NOT NULL   |
  | user_id    | bigint     | NOT NULL       |
  | company_id | uuid       | NOT NULL       |
  | created_at | timestamp  | NOT NULL       |
  | created_by | bigint     | NOT NULL       |
  | updated_at | timestamp  |                |
  | updated_by | bigint     |                |
  | deleted_at | timestamp  |                |
  | deleted_by | bigint     |                |

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
👨‍👩‍👧 유저: 로그인, 회원가입, JWT 인증, 권한 관리
🏪 허브: 허브 생성/조회/관리, 허브관리자 생성, 허브 경로 관리
🍱 업체: 업체 생성/조회/관리, 업체담당자 생성/조회
🛒 상품: 상품 생성/조회, 상품 정보 및 재고 관리
🎁 주문: 주문 생성/조회, 주문 상태 관리
📦 배송: 배송 생성/조회/관리, 배송담당자 생성/조회/관리, 배송 경로 조회/관리
💳 AI : 프롬프트 생성/조회/관리
⭐ SLACK: 메세지 조회/관리, 워크스페이스 참가여부 확인
```
<details>
  <summary><strong>1️⃣ 사용자 및 JWT 인증/인가</strong></summary>

- ✅ Spring Security와 JWT를 활용한 Stateless 인증
- ✅ 토큰 기반 인증으로 확장성 확보
- ✅ 로그인 및 기본 회원가입 기능 제공

</details>

<details>
  <summary><strong>2️⃣ 허브</strong></summary>

- ✅ 허브 생성 및 조회
- ✅ 다익스트라 알고리즘을 통한 허브 경로 플랜 구현
- ✅ 허브 담당자 배정 및 조회
- ✅ 허브 및 허브 담당자 관리

</details>

<details>
  <summary><strong>3️⃣ 업체</strong></summary>

- ✅ 업체 생성 및 상세 조회/검색 기능
- ✅ 업체 상태 관리 기능 제공
- ✅ 업체 담당자 생성
- ✅ RoleValidator 를 통한 인가처리

</details>

<details>
  <summary><strong>4️⃣ 상품</strong></summary>

- ✅ 검색 기능
- ✅ 상품 생성 및 정보 관리
- ✅ 재고 관리 기능

</details>

<details>
  <summary><strong>5️⃣ 주문</strong></summary>

- ✅ 주문 생성 및 상태 관리
- ✅ 주문에 따른 이벤트 발행(to 상품, 주문, 허브 도메인)
- ✅ 주문 조회(단건, 내역) 기능

</details>

<details>
  <summary><strong>6️⃣ 배송</strong></summary>

- ✅ 배송 생성 및 조회와 상태 관리
- ✅ 배송에 따른 담당자 할당 및 관련 데이터 조회/관리 기능 제공
- ✅ 배송 경로 조회 및 관리
- ✅ 생성·상태 변경 로그 관리
- ✅ 배송 시퀀스 및 경로 관리

</details>

<details>
  <summary><strong>7️⃣ AI</strong></summary>

- ✅ Google Gemini API 연동
- ✅ AI 호출 로그 저장
- ✅ 프롬프트 생성 및 조회
- ✅ 프롬프트 상태 로그 관리

</details>

<details>
  <summary><strong>8️⃣ Slack</strong></summary>

- ✅ Slack API 연동
- ✅ 메시지 조회
- ✅ 메시지 발송 상태 관리
- ✅ 워크스페이스 참가 여부 체크

</details>

<br>
