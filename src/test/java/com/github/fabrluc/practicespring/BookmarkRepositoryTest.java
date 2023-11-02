package com.github.fabrluc.practicespring;

import com.github.fabrluc.practicespring.entities.Bookmark;
import com.github.fabrluc.practicespring.repository.BookmarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:15.4-alpine:///db"
})
@ImportAutoConfiguration(JdbcClientAutoConfiguration.class)
@Sql("/test_data.sql")
public class BookmarkRepositoryTest {

    @Autowired
    JdbcClient jdbcClient;

    BookmarRepository bookmarRepository;

    @BeforeEach
    void setup() {
        bookmarRepository = new BookmarRepository(jdbcClient);
    }

    @Test
    void shouldFindAllBookmarks() {
        var bookmarks = bookmarRepository.findAll();
        assertThat(bookmarks).isNotEmpty();
        assertThat(bookmarks).hasSize(6);
    }

    @Test
    void shouldCreateBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        var id = bookmarRepository.save(bookmark);

        assertThat(id).isNotNull();
    }

    @Test
    void shouldGetBookmarkById() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        var id = bookmarRepository.save(bookmark);

        Optional<Bookmark> optionalBookmark = bookmarRepository.findById(id);
        assertThat(optionalBookmark).isPresent();
        assertThat(optionalBookmark.get().title()).isEqualTo(bookmark.title());
        assertThat(optionalBookmark.get().url()).isEqualTo(bookmark.url());
        assertThat(optionalBookmark.get().createdAt()).isEqualTo(bookmark.createdAt());
    }

    @Test
    void shouldDeleteBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        var id = bookmarRepository.save(bookmark);

        bookmarRepository.delete(id);

        var result = bookmarRepository.findById(id);
        assertThat(result).isNotPresent();
    }

    @Test
    void shouldUpdateBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        var id = bookmarRepository.save(bookmark);

        Bookmark update = new Bookmark(id, "teste", "www.google.com", Instant.now());
        bookmarRepository.update(update);

        var result = bookmarRepository.findById(id);
        assertThat(result).isPresent();
        assertThat(result.get().title()).isEqualTo(update.title());
        assertThat(result.get().url()).isEqualTo(update.url());

        assertThat(result.get().title()).isNotEqualTo(bookmark.title());
        assertThat(result.get().url()).isNotEqualTo(bookmark.url());
    }
}
