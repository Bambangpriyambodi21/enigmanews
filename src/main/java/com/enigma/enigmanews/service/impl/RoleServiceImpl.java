package com.enigma.enigmanews.service.impl;

import com.enigma.enigmanews.constant.ERole;
import com.enigma.enigmanews.entity.Role;
import com.enigma.enigmanews.model.response.RoleResponse;
import com.enigma.enigmanews.repository.RoleRepository;
import com.enigma.enigmanews.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getOrSave(ERole eRole) {
        Optional<Role> optionalRole = roleRepository.findByRole(eRole);
        if (optionalRole.isPresent()) return optionalRole.get();

        Role role = Role.builder()
                .role(eRole)
                .build();
        return roleRepository.save(role);

    }
}
