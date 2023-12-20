package com.example.JWT.service.ServiceImpl;

import com.example.JWT.config.JwtService;
import com.example.JWT.dto.Response.AuthenticationResponse;
import com.example.JWT.dto.Request.RegisterRequest;
import com.example.JWT.model.Entity.User;
import com.example.JWT.model.Enum.Role;
import com.example.JWT.model.Entity.Token;
import com.example.JWT.model.Enum.TokenType;
import com.example.JWT.repository.TokenRepository;
import com.example.JWT.repository.userRepository;
import com.example.JWT.service.Interface.IAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final userRepository repository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
           var User = com.example.JWT.model.Entity.User.builder()
                   .fullname(request.getFullname())
                   .username(request.getUsername())
                   .password(passwordEncoder.encode(request.getPassword()))
                   .email(request.getEmail())
                   .phone(request.getPhone())
                   .status(request.getStatus())
                   .updated_at(Date.valueOf(LocalDate.now()))
                   .role(Role.TENANT)
                   .build();
           var savedUser = repository.save(User);
           var jwtToken = jwtService.generateToken(User);
           var refreshToken = jwtService.generateRefreshToken(User);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                   .token(jwtToken)
                .refreshToken(refreshToken)
                   .build();
    }

    @Override
    public AuthenticationResponse authenticate(RegisterRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(user,jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .role(user.getRole())
                .build();
    }

    @Override
    public void revokeAllUserToken(User user){
        var validToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validToken.isEmpty())
            return;
        validToken.forEach(t-> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validToken);
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .token_type(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
    @Override
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response
    ) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            var  user = this.repository.findByUsername(userEmail)
                    .orElseThrow();
            if(jwtService.isTokenValid(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);
                revokeAllUserToken(user);
                saveUserToken(user,refreshToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }

    }


}
