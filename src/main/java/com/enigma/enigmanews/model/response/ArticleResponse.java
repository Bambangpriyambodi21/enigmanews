package com.enigma.enigmanews.model.response;

import com.enigma.enigmanews.entity.Author;
import com.enigma.enigmanews.entity.Tag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponse {
    private String id;
    private String title;
    private String content;
    private String author;
    private List<String> tags;
}
