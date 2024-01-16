package com.enigma.enigmanews.repository;

import com.enigma.enigmanews.entity.Article;
import com.enigma.enigmanews.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, String>, JpaSpecificationExecutor<Article> {

//    @Query("SELECT A FROM ArticleTag A where A.article = '{id}'")
    List<ArticleTag> findByArticleId(String id);
    List<ArticleTag> findByTagId(String id);
}
