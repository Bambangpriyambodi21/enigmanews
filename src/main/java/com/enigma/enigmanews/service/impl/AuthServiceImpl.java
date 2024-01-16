package com.enigma.enigmanews.service.impl;

import com.enigma.enigmanews.constant.ERole;
import com.enigma.enigmanews.entity.Author;
import com.enigma.enigmanews.entity.Role;
import com.enigma.enigmanews.entity.UserCredential;
import com.enigma.enigmanews.model.request.AuthRequest;
import com.enigma.enigmanews.model.response.UserResponse;
import com.enigma.enigmanews.repository.AuthorRepository;
import com.enigma.enigmanews.repository.UserCredentialRepository;
import com.enigma.enigmanews.security.JwtUtils;
import com.enigma.enigmanews.service.AuthService;
import com.enigma.enigmanews.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthorRepository authorRepository;
//    private final CustomerService customerService;
//    private final ValidationUtils validationUtils;

    @PostConstruct
    public void initSuperAdmin() {
        Optional<UserCredential> optionalUserCredential = userCredentialRepository
                .findByEmail("superadmin@gmail.com");

        if (optionalUserCredential.isPresent()) return;

        Role roleSuperAdmin = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);
        Role roleAuthor = roleService.getOrSave(ERole.ROLE_AUTHOR);

        // hash password
        String hashPassword = passwordEncoder.encode("password");

        // simpan ke db
        UserCredential userCredential = UserCredential.builder()
                .email("superadmin@gmail.com")
                .password(hashPassword)
                .roles(List.of(roleSuperAdmin, roleAdmin, roleAuthor))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);
    }

    @Override // register customer
    @Transactional(rollbackFor = Exception.class)
    public UserResponse register(AuthRequest request) {
//        validationUtils.validate(request);
        // buat role
        Role roleAuthor = roleService.getOrSave(ERole.ROLE_AUTHOR);

        // hash password
        String hashPassword = passwordEncoder.encode(request.getPassword());

        // simpan ke db
        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleAuthor))
                .build();

        UserCredential userCredential1=userCredentialRepository.save(userCredential);

        Author author = Author.builder()
                .name(request.getEmail())
                .userCredential(userCredential1)
                .build();
        authorRepository.saveAndFlush(author);


        return toUserResponse(userCredential);
    }

    @Override
    public UserResponse registerAdmin(AuthRequest request) {
//        validationUtils.validate(request);

        // buat role
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);

        // hash password
        String hashPassword = passwordEncoder.encode(request.getPassword());

        // simpan ke db
        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleAdmin))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);

        return toUserResponse(userCredential);
    }

    @Override
    public String login(AuthRequest request) {
//        validationUtils.validate(request);

//         logic dari spring security untuk authentication ini dipanggil disini
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // simpan sesi user di database spring boot sementara
        // yang bertujuan untuk mengakses resource tertentu dikemudian
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserCredential userCredential = (UserCredential) authenticate.getPrincipal();

//        Optional<UserCredential> userCredential = userCredentialRepository.findByEmail(request.getEmail());
//        if (userCredential.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
//
//        log.info(userCredential.get().getEmail());
        return jwtUtils.generateToken(userCredential);
    }
//
    private static UserResponse toUserResponse(UserCredential userCredential) {
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
                .email(userCredential.getEmail())
                .roles(roles)
                .build();
    }

}
