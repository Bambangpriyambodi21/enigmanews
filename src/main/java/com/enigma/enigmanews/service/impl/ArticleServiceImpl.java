package com.enigma.enigmanews.service.impl;

import com.enigma.enigmanews.entity.Article;
import com.enigma.enigmanews.entity.ArticleTag;
import com.enigma.enigmanews.entity.Author;
import com.enigma.enigmanews.entity.Tag;
import com.enigma.enigmanews.model.AuthorClaimId;
import com.enigma.enigmanews.model.request.ArticleRequest;
import com.enigma.enigmanews.model.request.AuthorClaim;
import com.enigma.enigmanews.model.request.SearchArticleRequest;
import com.enigma.enigmanews.model.response.ArticleResponse;
import com.enigma.enigmanews.repository.ArticleRepository;
import com.enigma.enigmanews.repository.ArticleTagRepository;
import com.enigma.enigmanews.repository.AuthorRepository;
import com.enigma.enigmanews.repository.TagRepository;
import com.enigma.enigmanews.security.JwtUtils;
import com.enigma.enigmanews.service.ArticleService;
import com.enigma.enigmanews.service.UserService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final AuthorRepository authorRepository;
    private final ArticleTagRepository articleTagRepository;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleResponse createArticle(ArticleRequest articleRequest, String request) {

        Optional<Author> idAuthorByName = authorRepository.findById(articleRequest.getAuthor_id());

        AuthorClaim author = AuthorClaim.builder()
                .authorId(idAuthorByName.get())
                .build();

        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .author(author.getAuthorId())
                .content(articleRequest.getContent())
                .build();

        Article article1 = articleRepository.save(article);

        List<String> taga = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        for (int i=0;i<articleRequest.getTags().size(); i++){

            Tag idByName = tagRepository.findIdByName(articleRequest.getTags().get(i).getName());
            Tag tag = Tag.builder()
                    .name(articleRequest.getTags().get(i).getName())
                    .build();
            if (idByName.getId().isBlank()) {

                Tag tag1 = tagRepository.save(tag);
                tags.add(tag1);
            }
            taga.add(articleRequest.getTags().get(i).getName());
        }

        ArticleResponse articleResponse = ArticleResponse.builder()
                .id(article1.getId())
                .title(article1.getTitle())
                .content(article1.getContent())
                .author(articleRequest.getAuthor_id())
                .tags(taga)
                .build();

        Article article2 = Article.builder()
                .id(article1.getId())
                .build();

        for (int j=0;j<tags.size();j++){
            Tag tag = Tag.builder()
                    .id(tags.get(j).getId())
                    .build();
            ArticleTag articleTag = ArticleTag.builder()
                    .article(article2)
                    .tag(tag)
                    .build();
        articleTagRepository.saveAndFlush(articleTag);
        }

        return articleResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleResponse updateArticle(String id, ArticleRequest articleRequest) {

        Optional<Author> authora = authorRepository.findById(articleRequest.getAuthor_id());

        log.info(authora.get().getId());
        List<ArticleTag> optionalArticleTag = articleTagRepository.findByArticleId(id);

        List<String> tags = new ArrayList<>();

        log.info(String.valueOf(articleRequest.getTags().size()));
        for (int i=0; i<=articleRequest.getTags().size()-1; i++) {

            Optional<Tag> tagName = tagRepository.findById(articleRequest.getTags().get(i).getId());
            log.info(tagName.get().getName());

            Tag tagId = Tag.builder()
                    .id(articleRequest.getTags().get(i).getId())
                    .name(tagName.get().getName())
                    .build();

                ArticleTag articleTag = ArticleTag.builder()
                        .id(optionalArticleTag.get(i).getId())
                        .tag(tagId)
                        .article(optionalArticleTag.get(i).getArticle())
                        .build();

                articleTagRepository.saveAndFlush(articleTag);

                String tagIdString = tagId.getId();

                log.info(tagName.get().getName());
                Tag tag = Tag.builder()
                        .id(articleTag.getId())
                        .name(tagName.get().getName())
                        .build();
                tags.add(tag.getName());

        }

        Author author1 = Author.builder()
                .id(authora.get().getId())
                .name(authora.get().getName())
                .build();

        log.info(authora.get().getName());

        Article article1 = Article.builder()
                .id(id)
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .author(author1)
                .build();
        articleRepository.save(article1);

        ArticleResponse articleResponse = ArticleResponse.builder()
                .id(article1.getId())
                .title(article1.getTitle())
                .content(article1.getContent())
                .author(article1.getAuthor().getName())
                .tags(tags)
                .build();
        return articleResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteArticle(String id) {

        articleRepository.deleteById(id);

        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Article> findArticle(SearchArticleRequest searchArticleRequest) {

//        Author idAuthorByName = authorRepository.findIdAuthorByName(searchArticleRequest.getAuthor());
//
//        List<Article> articleByTagAndAuthor = articleRepository.findArticleByTagAndAuthor(idAuthorByName.getId());
//
//        return articleByTagAndAuthor;
        Author idAuthora = authorRepository.findIdAuthorByName(searchArticleRequest.getAuthor());
        log.info(idAuthora.getId());

        PageRequest pageable = PageRequest.of(0,5);
        Specification<Article> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Author idAuthore = authorRepository.findIdAuthorByName(searchArticleRequest.getAuthor());
            log.info(idAuthore.getId());
            AuthorClaimId authorClaimIdi = AuthorClaimId.builder()
                    .authorId(idAuthore.getId())
                    .build();
            Predicate namePredicatei = criteriaBuilder.equal(root.get("author"), authorClaimIdi);
            log.info(namePredicatei.toString());
            predicates.add(namePredicatei);

            if (searchArticleRequest.getAuthor()!= null){
                Author idAuthor = authorRepository.findIdAuthorByName(searchArticleRequest.getAuthor());
                log.info(idAuthor.getId());
                AuthorClaimId authorClaimId = AuthorClaimId.builder()
                        .authorId(idAuthor.getId())
                        .build();
                Predicate namePredicate = criteriaBuilder.equal(root.get("author"), authorClaimId);
                predicates.add(namePredicate);
            }
            if (searchArticleRequest.getTags()!= null){
                for (int i=0;i<searchArticleRequest.getTags().size();i++){
                    List<ArticleTag> byTagId = articleTagRepository.findByTagId(searchArticleRequest.getTags().get(i));
                    Predicate namePredicate = criteriaBuilder.like(root.get("id"), byTagId.get(i).getArticle().getId());
                    predicates.add(namePredicate);
                }
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        return articleRepository.findAll(specification, pageable);
    }


}
