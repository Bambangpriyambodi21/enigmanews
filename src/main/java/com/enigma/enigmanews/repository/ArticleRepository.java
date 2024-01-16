package com.enigma.enigmanews.repository;

import com.enigma.enigmanews.entity.Article;
import com.enigma.enigmanews.model.response.ArticleResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

    List<Article> findArticlesByAuthorId(String author);

    @Query("Select a from Article a where a.author= :id")
    List<Article> findArticleByTagAndAuthor(String id);
}
