package com.enigma.enigmanews.service;

import com.enigma.enigmanews.entity.UserCredential;
import com.enigma.enigmanews.model.request.AuthRequest;
import com.enigma.enigmanews.model.response.ArticleResponse;
import com.enigma.enigmanews.model.response.UserResponse;

public interface AuthService {
    UserResponse register(AuthRequest request);
    UserResponse registerAdmin(AuthRequest request);
    String login(AuthRequest request);
}
