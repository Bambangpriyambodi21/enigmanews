package com.enigma.enigmanews.model.request;

import com.enigma.enigmanews.entity.Author;
import com.enigma.enigmanews.entity.Tag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRequest {
    private String title;
    private String content;
    private String author_id;
    private List<Tag> tags;
}
