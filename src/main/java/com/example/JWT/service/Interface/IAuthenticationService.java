package com.example.JWT.service.Interface;

import com.example.JWT.dto.Response.AuthenticationResponse;
import com.example.JWT.dto.Request.RegisterRequest;
import com.example.JWT.model.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(RegisterRequest request);
    public void revokeAllUserToken(User user);
    public void saveUserToken(User user, String jwtToken);
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
