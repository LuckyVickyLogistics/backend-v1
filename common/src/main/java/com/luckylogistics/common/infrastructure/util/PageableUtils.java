package com.luckylogistics.common.infrastructure.util;


import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final Set<Integer> ALLOWED_PAGE_SIZES = Set.of(10, 30, 50);
    private static final String DEFAULT_SORT_BY = "createdAt";
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("createdAt", "updatedAt");

    /**
     * Pageable 생성
     *
     * @param page      페이지 번호 (0부터 시작)
     * @param size      페이지 크기 (10, 30, 50만 허용)
     * @param sortBy    정렬 기준 필드 (허용 목록 외는 createdAt으로 대체)
     * @param direction 정렬 방향 (ASC/DESC)
     */
    public static Pageable createPageable(int page, int size, String sortBy, Sort.Direction direction) {
        int validatedSize = validatePageSize(size);
        String validatedSortBy = validateSortBy(sortBy);
        Sort.Direction finalDirection = (direction != null) ? direction : Sort.Direction.DESC;

        return PageRequest.of(page, validatedSize, Sort.by(finalDirection, validatedSortBy));
    }

    /** createdAt 기준 기본 Pageable */
    public static Pageable createPageableWithCreatedAt(int page, int size, Sort.Direction direction) {
        int validatedSize = validatePageSize(size);
        return PageRequest.of(page, validatedSize, Sort.by(direction, DEFAULT_SORT_BY));
    }


    /** 페이지 크기 검증: 10, 30, 50 이외는 10으로 강제 */
    private static int validatePageSize(int size) {
        if (!ALLOWED_PAGE_SIZES.contains(size)) {
            return DEFAULT_PAGE_SIZE;
        }
        return size;
    }

    /** 정렬 기준 검증: 허용된 필드 외는 createdAt 기본값 사용 */
    private static String validateSortBy(String sortBy) {
        if (sortBy == null || !ALLOWED_SORT_FIELDS.contains(sortBy)) {
            return DEFAULT_SORT_BY;
        }
        return sortBy;
    }
}
