package com.example.JWT.controller;

import com.example.JWT.dto.Request.ContractRequest;
import com.example.JWT.dto.Response.UserResponse;
import com.example.JWT.service.ServiceImpl.ContractService;
import com.example.JWT.service.ServiceImpl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ContractService contractService;


    @PostMapping("/booking/{code}")
    public ResponseEntity<UserResponse> bookingBedsitter(
            @PathVariable String code
            ){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/confirm_booking")
   public ResponseEntity<String> confirmBooking(
           @RequestBody ContractRequest request
    ){
        try {
            contractService.createContract(request);
            return new ResponseEntity<>("Contract created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create contract", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
