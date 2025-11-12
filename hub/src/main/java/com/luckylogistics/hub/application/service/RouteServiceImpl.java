package com.luckylogistics.hub.application.service;

import com.luckylogistics.hub.application.dto.RoutePlanRequest;
import com.luckylogistics.hub.application.dto.RoutePlanResponse;
import com.luckylogistics.hub.application.dto.RouteSegmentResponse;
import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.model.HubConnection;
import com.luckylogistics.hub.domain.repository.HubConnectionRepository;
import com.luckylogistics.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteServiceImpl implements RouteService {

    private final HubRepository hubRepository;
    private final HubConnectionRepository hubConnectionRepository;

    @Override
    public RoutePlanResponse plan(RoutePlanRequest request) {
        UUID start = request.departureHubId();
        UUID end   = request.arrivalHubId();

        if (start == null || end == null) {
            throw new IllegalArgumentException("출발/도착 허브 ID는 필수입니다.");
        }

        if (start.equals(end)) {
            // 동일 허브: 거리/시간 0, 구간 없음
            return new RoutePlanResponse(List.of(), 0.0, 0);
        }

        // 모든 간선 조회 후 from 기준 인접 리스트 구성
        List<HubConnection> edges = hubConnectionRepository.findAll();
        Map<UUID, List<HubConnection>> adj = edges.stream()
                .collect(Collectors.groupingBy(HubConnection::fromId));

        // 최적화 기준 가중치
        ToDoubleFunction<HubConnection> weight =
                (request.optimizeBy() == RoutePlanRequest.OptimizeBy.TIME)
                        ? (e -> (double) e.getTime())
                        : HubConnection::getDistance;

        // 다익스트라 준비
        Map<UUID, Double> dist = new HashMap<>();
        Map<UUID, UUID>   prev = new HashMap<>();
        Set<UUID>         visited = new HashSet<>();

        // 우선순위큐: 현재까지의 최단거리 기준
        PriorityQueue<UUID> pq = new PriorityQueue<>(
                Comparator.comparingDouble(id -> dist.getOrDefault(id, Double.POSITIVE_INFINITY))
        );

        dist.put(start, 0.0);
        pq.offer(start);

        // 다익스트라 본문
        while (!pq.isEmpty()) {
            UUID cur = pq.poll();
            if (!visited.add(cur)) continue;      // 이미 처리된 노드면 스킵
            if (Objects.equals(cur, end)) break;  // 도착

            for (HubConnection e : adj.getOrDefault(cur, List.of())) {
                UUID nxt = e.toId();
                double cand = dist.get(cur) + weight.applyAsDouble(e);
                if (cand < dist.getOrDefault(nxt, Double.POSITIVE_INFINITY)) {
                    dist.put(nxt, cand);
                    prev.put(nxt, cur);
                    pq.offer(nxt); // decrease-key 대용으로 재삽입
                }
            }
        }

        // 경로 복원 가능 여부 체크
        if (!start.equals(end) && !prev.containsKey(end)) {
            throw new IllegalArgumentException("경로가 존재하지 않습니다. (from=" + start + ", to=" + end + ")");
        }

        // 경로 복원
        List<UUID> path = new ArrayList<>();
        for (UUID at = end; at != null; at = prev.get(at)) path.add(at);
        Collections.reverse(path);

        if (path.size() < 2) {
            // start==end 처리가 위에 있지만, 안전 가드
            return new RoutePlanResponse(List.of(), 0.0, 0);
        }

        // 허브 캐시
        Map<UUID, Hub> hubCache = hubRepository.findAll().stream()
                .collect(Collectors.toMap(Hub::getHubId, h -> h));

        // 세그먼트 생성
        List<RouteSegmentResponse> segments = new ArrayList<>();
        double totalDist = 0.0;
        int totalTime = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            UUID a = path.get(i);
            UUID b = path.get(i + 1);

            HubConnection edge = hubConnectionRepository
                    .findByFromIdAndToId(a, b)
                    .orElseThrow(() -> new IllegalStateException("경로 간선 데이터가 없습니다: " + a + " -> " + b));

            Hub ha = Optional.ofNullable(hubCache.get(a))
                    .orElseThrow(() -> new IllegalStateException("허브가 존재하지 않습니다: " + a));
            Hub hb = Optional.ofNullable(hubCache.get(b))
                    .orElseThrow(() -> new IllegalStateException("허브가 존재하지 않습니다: " + b));

            totalDist += edge.getDistance();
            totalTime += edge.getTime();

            segments.add(new RouteSegmentResponse(
                    i + 1,
                    a,
                    b,
                    edge.getDistance(),
                    edge.getTime()
            ));
        }

        return new RoutePlanResponse(segments, totalDist, totalTime);
    }
}
