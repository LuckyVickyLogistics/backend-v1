CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS hubs;
SET search_path TO hubs;

INSERT INTO hubs.p_hub (hub_id, name, address, latitude, longitude, created_at, created_by, updated_at, updated_by, is_deleted) VALUES
                                                                        (gen_random_uuid(),'서울특별시 센터','서울특별시 송파구 송파대로 55', 37.51457500, 127.10561700, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'경기 북부 센터','경기도 고양시 덕양구 권율대로 570', 37.65835500, 126.83167600, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'경기 남부 센터','경기도 이천시 덕평로 257-21', 37.20915300, 127.48667800, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'부산광역시 센터','부산 동구 중앙대로 206', 35.13417500, 129.05606000, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'대구광역시 센터','대구 북구 태평로 161', 35.88361600, 128.58297900, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'인천광역시 센터','인천 남동구 정각로 29', 37.44733600, 126.70762900, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'광주광역시 센터','광주 서구 내방로 111', 35.15513700, 126.87703800, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'대전광역시 센터','대전 서구 둔산로 100', 36.35170300, 127.37898500, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'울산광역시 센터','울산 남구 중앙로 201', 35.53837700, 129.31117900, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'세종특별자치시 센터','세종특별자치시 한누리대로 2130', 36.48035100, 127.28999100, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'강원특별자치도 센터','강원특별자치도 춘천시 중앙로 1', 37.88131500, 127.72978800, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'충청북도 센터','충북 청주시 상당구 상당로 82', 36.63691700, 127.49115200, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'충청남도 센터','충남 홍성군 홍북읍 충남대로 21', 36.60183900, 126.65997800, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'전북특별자치도 센터','전북특별자치도 전주시 완산구 효자로 225', 35.82127900, 127.14749200, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'전라남도 센터','전남 무안군 삼향읍 오룡길 1', 34.99360800, 126.48527400, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'경상북도 센터','경북 안동시 풍천면 도청대로 455', 36.56832000, 128.72438700, current_timestamp,1, current_timestamp, 1, false),
                                                                        (gen_random_uuid(),'경상남도 센터','경남 창원시 의창구 중앙대로 300', 35.22681000, 128.68192700, current_timestamp,1, current_timestamp, 1, false)
ON CONFLICT (address) DO NOTHING;

WITH
-- 1) 원본(단방향) 정의: 네가 제공한 연결 목록
edges_base (from_name, to_name) AS (
    VALUES
        -- 경기 남부 → 인접
        ('경기 남부 센터','경기 북부 센터'),
        ('경기 남부 센터','서울특별시 센터'),
        ('경기 남부 센터','인천광역시 센터'),
        ('경기 남부 센터','강원특별자치도 센터'),
        ('경기 남부 센터','경상북도 센터'),
        ('경기 남부 센터','대전광역시 센터'),
        ('경기 남부 센터','대구광역시 센터'),

        -- 대전 → 인접
        ('대전광역시 센터','충청남도 센터'),
        ('대전광역시 센터','충청북도 센터'),
        ('대전광역시 센터','세종특별자치시 센터'),
        ('대전광역시 센터','전북특별자치도 센터'),
        ('대전광역시 센터','광주광역시 센터'),
        ('대전광역시 센터','전라남도 센터'),
        ('대전광역시 센터','경기 남부 센터'),
        ('대전광역시 센터','대구광역시 센터'),

        -- 대구 → 인접 (중복 방향/노드는 이후 DISTINCT로 정리)
        ('대구광역시 센터','경상북도 센터'),
        ('대구광역시 센터','경상남도 센터'),
        ('대구광역시 센터','부산광역시 센터'),
        ('대구광역시 센터','울산광역시 센터'),
        ('대구광역시 센터','경상북도 센터'), -- 중복 하나 더
        ('대구광역시 센터','경기 남부 센터'),
        ('대구광역시 센터','대전광역시 센터'),

        -- 경상북도 → 인접
        ('경상북도 센터','경기 남부 센터'),
        ('경상북도 센터','대구광역시 센터')
),
-- 2) 양방향으로 확장
edges_bi (from_name, to_name) AS (
    SELECT from_name, to_name FROM edges_base
    UNION ALL
    SELECT to_name   AS from_name,
           from_name AS to_name
    FROM edges_base
),
-- 3) 중복 제거
edges AS (
    SELECT DISTINCT from_name, to_name FROM edges_bi
),
-- 4) 허브 ID/좌표 조인 + 거리/시간 계산
calc AS (
    SELECT
        f.hub_id AS from_hub_id,
        t.hub_id AS to_hub_id,
        /* Haversine 직선거리 (km) */
        (
            6371.0088 * 2 * ASIN(
                    SQRT(
                            POWER(SIN(RADIANS((t.latitude  - f.latitude )/2)), 2) +
                            COS(RADIANS(f.latitude)) * COS(RADIANS(t.latitude)) *
                            POWER(SIN(RADIANS((t.longitude - f.longitude)/2)), 2)
                    )
                            )
            ) AS distance_km_raw
    FROM edges e
             JOIN hubs.p_hub f ON f.name = e.from_name
             JOIN hubs.p_hub t ON t.name = e.to_name
)
INSERT INTO hubs.p_hub_route
(route_id, from_hub_id, to_hub_id, time, distance, created_at, created_by, updated_at, updated_by, is_deleted)
SELECT
    gen_random_uuid()                            AS route_id,
    c.from_hub_id,
    c.to_hub_id,
    /* 시간(분) = 거리 / 70(km/h) * 60 */
    CAST(ROUND((c.distance_km_raw / 70.0) * 60.0) AS INTEGER) AS time_minutes,
    ROUND(c.distance_km_raw::numeric, 1)         AS distance_km,
    current_timestamp                             AS created_at,
    1                                            AS created_by,
    current_timestamp                             AS updated_at,
    1                                            AS updated_by,
    false                                        AS is_deleted
FROM calc c
-- 같은 (from,to) 있으면 생략 (권장: p_hub_route에 UNIQUE(from_hub_id, to_hub_id) 존재)
    ON CONFLICT DO NOTHING;