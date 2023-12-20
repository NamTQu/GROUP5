package com.example.JWT.controller;


import com.example.JWT.dto.Request.ChangePasswordRequest;
import com.example.JWT.dto.Request.ContractRequest;
import com.example.JWT.dto.Request.ResetPasswordRequest;
import com.example.JWT.dto.Response.UserResponse;
import com.example.JWT.model.Entity.Contract;
import com.example.JWT.model.Entity.User;
import com.example.JWT.service.ServiceImpl.ContractService;
import com.example.JWT.service.ServiceImpl.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ContractService contractService;


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

    @DeleteMapping("/delete_booking/{contractId}")
    public ResponseEntity<String> cancelContract(@PathVariable Long contractId){
        try {
            contractService.cancelContract(contractId);
            return new ResponseEntity<>("Contract canceled successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to cancel contract", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/booking_history")
    public ResponseEntity<?> getContractHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
          return  ResponseEntity.ok(contractService.getUserBookingHistory(page,size));
    }


}

