package com.enigma.enigmanews.service;

import com.enigma.enigmanews.entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCredential loadByUserId(String userId);
}
