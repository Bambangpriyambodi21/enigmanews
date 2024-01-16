package com.enigma.enigmanews.service;

import com.enigma.enigmanews.entity.Article;
import com.enigma.enigmanews.model.request.ArticleRequest;
import com.enigma.enigmanews.model.request.SearchArticleRequest;
import com.enigma.enigmanews.model.response.ArticleResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ArticleService {

    ArticleResponse createArticle(ArticleRequest articleRequest, String request);
    ArticleResponse updateArticle(String id, ArticleRequest articleRequest);
    String deleteArticle(String id);
    Page<Article> findArticle(SearchArticleRequest searchArticleRequest);
}
