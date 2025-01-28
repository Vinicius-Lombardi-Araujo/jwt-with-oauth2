package com.varaujo.jwt_with_oauth2.dto;

import java.util.List;

public record FeedDto(List<FeedItemDto> feedItems, int page, int pageSize, int totalPages, long totalElements) {
}
