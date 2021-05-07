package com.yoga.authentication.service;

import com.yoga.authentication.model.request.UserRequest;

import java.util.Map;

public interface TokenService {
    String generateToken(UserRequest userRequest);
    Boolean validateToken(String token, String userId);
}
