package com.enigma.enigmanews.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_article_tag")
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JoinColumn(name = "article_id")
    @ManyToOne
    private Article article;

    @JoinColumn(name = "tag_id")
    @ManyToOne
    private Tag tag;
}
