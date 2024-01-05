package com.github.fabrluc.practicespring.repository;

import com.github.fabrluc.practicespring.entities.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
            + "VALUES (?#{[0]}, ?#{[1]}, ?#{[2] ?: 'Empty Article'}, :#{#language.toLowerCase()})",
            nativeQuery = true)
    void saveWithPositionalSpELArgumentsWithEmptyCheck(long id, String title, String content, @Param("language") String isoCode);

    /***
     * @Modifying
     * @Transactional
     * @Query(value = "INSERT INTO articles (id, title, content, language) "
     *   + "VALUES (:#{#wrapper.article.id}, :#{#wrapper.article.title}, "
     *   + ":#{#wrapper.article.content}, :#{#wrapper.article.language})",
     *   nativeQuery = true)
     * void saveWithSingleWrappedObjectSpELArgument(@Param("wrapper") ArticleWrapper articleWrapper);
     */
}
