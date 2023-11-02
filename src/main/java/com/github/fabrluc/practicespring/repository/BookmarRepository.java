package com.github.fabrluc.practicespring.repository;

import com.github.fabrluc.practicespring.entities.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookmarRepository {

    private static final String BOOKMARK_NOT_FOUND = "bookmark not found";
    private final JdbcClient jdbcClient;

    @Transactional(readOnly = true)
    public List<Bookmark> findAll() {
        var sql = "select id, title, url, created_at from bookmarks";
        return jdbcClient.sql(sql).query(new BookmarkRowMapper()).list();
    }

    public Optional<Bookmark> findById(final Long id) {
        var sql = "select id, title, url, created_at from bookmarks where id = :id";
        return jdbcClient.sql(sql).param("id", id).query(Bookmark.class).optional();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long save(final Bookmark bookmark) {
        final String sql =  "insert into bookmarks(title, url, created_at) values (:title, :url, :createdAt) returning id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .param("title", bookmark.title())
                .param("url", bookmark.url())
                .param("createdAt", Timestamp.from(bookmark.createdAt()))
                .update(keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(final Bookmark bookmark) {
        final String sql =  "update bookmarks set title = ?, url = ? where id = ?";
        var count = jdbcClient.sql(sql)
                .param(1, bookmark.title())
                .param(2, bookmark.url())
                .param(3, bookmark.id())
                .update();

        if (count == 0) {
            throw new RuntimeException(BOOKMARK_NOT_FOUND);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        var sql = "delete from bookmarks where id = ?";
        var count = jdbcClient.sql(sql)
                .param(1, id)
                .update();

        if (count == 0) {
            throw new RuntimeException(BOOKMARK_NOT_FOUND);
        }
    }

    public static class BookmarkRowMapper implements RowMapper<Bookmark> {

        @Override
        public Bookmark mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Bookmark(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("url"),
                    rs.getTimestamp("created_at").toInstant()
            );
        }
    }
}
