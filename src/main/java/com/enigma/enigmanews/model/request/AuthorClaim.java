package com.enigma.enigmanews.model.request;

import com.enigma.enigmanews.entity.Author;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorClaim {
    private Author authorId;
}
