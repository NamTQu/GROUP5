package com.example.JWT.controller;


import com.example.JWT.dto.Request.ChangePasswordRequest;
import com.example.JWT.dto.Request.ResetPasswordRequest;
import com.example.JWT.dto.Response.UserResponse;
import com.example.JWT.model.Entity.User;
import com.example.JWT.service.ServiceImpl.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private UserResponse convertEntityToDTO(User user){
          return  modelMapper.map(user, UserResponse.class);
    }

    @PatchMapping("/change_password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ){
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok("change Password success");
    }

    @GetMapping("/reset_password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request
    ){
        return ResponseEntity.ok(userService.resetPassword(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> viewProfile(){
        UserResponse userResponse = userService.getUsers();
        if (userResponse == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<User> editProfile(@PathVariable Integer id, User updatedUser){
        User updatedUserData = userService.editProfile(id, updatedUser);
        if(updatedUserData != null){
            return ResponseEntity.ok(updatedUserData);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
