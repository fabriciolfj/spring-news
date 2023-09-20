package com.github.fabrluc.practicespring.entities;

import java.time.Instant;

public record Bookmark(Long id, String title, String url, Instant createdAt) {
}
