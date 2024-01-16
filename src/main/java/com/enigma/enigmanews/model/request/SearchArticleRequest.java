package com.enigma.enigmanews.model.request;

import com.enigma.enigmanews.entity.Tag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchArticleRequest {
    private String author;
    private List<String> tags;
}
