package com.enigma.enigmanews.model.response;

import com.enigma.enigmanews.constant.ERole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String id;
    private List<ERole> role;

}
