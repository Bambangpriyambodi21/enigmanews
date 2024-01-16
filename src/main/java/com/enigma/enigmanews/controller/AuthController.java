package com.enigma.enigmanews.controller;

import com.enigma.enigmanews.entity.UserCredential;
import com.enigma.enigmanews.model.request.AuthRequest;
import com.enigma.enigmanews.model.response.UserResponse;
import com.enigma.enigmanews.model.response.WebResponse;
import com.enigma.enigmanews.service.AuthService;
import com.enigma.enigmanews.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request){
        UserResponse userResponse = authService.register(request);
        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("uskse")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/register/admin")
    public ResponseEntity<?> admin(@RequestBody AuthRequest request){
        UserResponse userResponse = authService.registerAdmin(request);
        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("uskse")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        String userResponse = authService.login(request);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("uskse")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
