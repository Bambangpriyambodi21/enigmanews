package com.enigma.enigmanews.controller;

import com.enigma.enigmanews.entity.Article;
import com.enigma.enigmanews.model.request.ArticleRequest;
import com.enigma.enigmanews.model.request.SearchArticleRequest;
import com.enigma.enigmanews.model.response.ArticleResponse;
import com.enigma.enigmanews.model.response.WebResponse;
import com.enigma.enigmanews.service.ArticleService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping(path = "/article")
    public ResponseEntity<?> createArticle(@RequestBody ArticleRequest articleRequest,@RequestHeader("Authorization") String request){
        ArticleResponse articleResponse = articleService.createArticle(articleRequest, request);

        WebResponse<ArticleResponse> response = WebResponse.<ArticleResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succesfully created article")
                .data(articleResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/article/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable String id, @RequestBody ArticleRequest articleRequest){
        ArticleResponse articleResponse = articleService.updateArticle(id, articleRequest);

        WebResponse<ArticleResponse> response = WebResponse.<ArticleResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Succesfully created article")
                .data(articleResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/article/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable String id){
        String articleResponse = articleService.deleteArticle(id);

        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Succesfully delete article")
                .data(articleResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/article")
    public ResponseEntity<?> findArticle(@RequestParam(required = false) String author,
                                         @RequestParam(required = false) List<String> tag){
        SearchArticleRequest searchArticleRequest = SearchArticleRequest.builder()
                .author(author)
                .tags(tag)
                .build();

        Page<Article> articleResponse = articleService.findArticle(searchArticleRequest);

        WebResponse<Page<Article>> response = WebResponse.<Page<Article>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Succesfully find article")
                .data(articleResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
