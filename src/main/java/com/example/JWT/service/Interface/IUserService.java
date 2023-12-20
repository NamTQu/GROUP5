package com.example.JWT.service.Interface;

import com.example.JWT.dto.Request.ChangePasswordRequest;
import com.example.JWT.dto.Request.ResetPasswordRequest;
import com.example.JWT.dto.Response.BedsitterResponse;
import com.example.JWT.dto.Response.UserResponse;
import com.example.JWT.model.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface IUserService {
    public void changePassword(ChangePasswordRequest request, Principal connectedUser);
    public Object resetPassword(ResetPasswordRequest request);
    public User editProfile (Integer userId, User updatedUser);
    public Page<UserResponse> getAllUsers(int page, int size, String keyWord);
    public Integer getUserIdFromToken();

}
